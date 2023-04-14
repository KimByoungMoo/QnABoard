package QnABoard.member.service;

import QnABoard.member.entity.Member;
import QnABoard.member.repository.MemberRepository;
import QnABoard.auth.repository.RefreshTokenRepository;
import QnABoard.auth.utils.CustomAuthorityUtils;
import QnABoard.exception.BusinessLogicException;
import QnABoard.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils authorityUtils;
    private final RefreshTokenRepository refreshTokenRepository;

    public Member createMember(Member member) {
        verifyExistsEmail(member.getEmail());
        verifyExistsNickName(member.getNickname());

        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encryptedPassword);

        List<String> roles = authorityUtils.createRoles(member.getEmail());
        member.setRoles(roles);

        Member savedMember = memberRepository.save(member);

        return savedMember;
    }

    public Member updateMemberInfo(Member member) {
        Member findMember = findByEmail();

        if (member.getPassword() != null) {
            findMember.setPassword(passwordEncoder.encode(member.getPassword()));
        }

        Optional.ofNullable(member.getNickname()).ifPresent(username -> findMember.setNickname(username));
        verifyExistsNickName(member.getNickname());
        Optional.ofNullable(member.getComment()).ifPresent(comment -> findMember.setComment(comment));

        return memberRepository.save(findMember);
    }

    @Transactional(readOnly = true)
    public Member findMember (long memberId) {
        Member authMember = findByEmail();

        Member member = authMember.getMemberId() == memberId ? authMember : findVerifiedMember(memberId);

        return member;
    }

    @Transactional(readOnly = true)
    public Member findVerifiedMember(long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member findMember = optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        return findMember;
    }

    @Transactional
    public void logout (HttpServletRequest request) {
        String accessToken = getAccessToken(request);
        String email = getCurrentMemberEmail();

        refreshTokenRepository.setBlackList(accessToken);
        refreshTokenRepository.deleteBy(email);
    }

    public void deleteMember(HttpServletRequest request) {
        String accessToken = getAccessToken(request);

        Member findMember = findByEmail();

        memberRepository.delete(findMember);

        refreshTokenRepository.deleteBy(findMember.getEmail());
        refreshTokenRepository.setBlackList(accessToken);
    }


    private void verifyExistsEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent())
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
    }

    private void verifyExistsNickName(String nickname) {
        Optional<Member> member = memberRepository.findByNickname(nickname);
        if (member.isPresent())
            throw new BusinessLogicException(ExceptionCode.MEMBER_NICKNAME_EXISTS);
    }

    public Member findByEmail() {
        String email = getCurrentMemberEmail();
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        return optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.TOKEN_NOT_VALID));
    }

    @Transactional
    public Member findByEmail(String email){
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        return optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    public String getCurrentMemberEmail() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private String getAccessToken(HttpServletRequest request) {
        return request.getHeader("Authorization").substring(7);
    }
}
