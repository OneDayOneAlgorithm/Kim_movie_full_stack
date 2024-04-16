package com.example.kim_movie.application;

import com.example.kim_movie.controller.request.MemberRequest;
import com.example.kim_movie.controller.response.MemberResponse;
import com.example.kim_movie.domain.model.Member;
import com.example.kim_movie.domain.model.MemberFollowing;
import com.example.kim_movie.domain.repository.MemberFollowingRepository;
import com.example.kim_movie.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberAppService {
    private final MemberRepository memberRepository;
    private final MemberFollowingRepository memberFollowingRepository;

    public MemberResponse.Detail createMember(MemberRequest.Create request) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDateTime = currentDateTime.format(formatter);

        Member member = Member.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .username(request.getUsername())
                .dateJoined(formattedDateTime)
                .isStaff(false)
                .build();

        memberRepository.save(member);

        return MemberResponse.Detail.of(member);
    }

    public MemberResponse.Detail updatemember(Long id, MemberRequest.Update request) {
        Member member = memberRepository.findById(id).orElseThrow();
        member.setPassword(request.getPassword());
        member.setUsername(request.getUsername());
        memberRepository.save(member);
        return MemberResponse.Detail.of(member);
    }

    public MemberResponse.Detail retrieveDetail(Long id) {
        Member member = memberRepository.findById(id).orElseThrow();
        return MemberResponse.Detail.of(member);
    }

    public List<MemberResponse.Detail> retrieveList() {
        return memberRepository.findAll().stream().map(MemberResponse.Detail::of).toList();
    }

    public Boolean followMember(Long fromId, Long toId) {
        Member fromMember = memberRepository.findById(fromId).orElseThrow();
        Member toMember = memberRepository.findById(toId).orElseThrow();
        MemberFollowing memberFollowing = memberFollowingRepository.findByFromMemberAndToMember(fromMember, toMember);
        if (memberFollowing == null) {
            memberFollowing = MemberFollowing.builder()
                    .fromMember(fromMember)
                    .toMember(toMember)
                    .following(true)
                    .build();
        } else {
            memberFollowing.setFollowing(!memberFollowing.getFollowing());
        }
        memberFollowingRepository.save(memberFollowing);
        return memberFollowing.getFollowing();
    }

    public Boolean deleteMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow();
        memberRepository.delete(member);
        return true;
    }

    public MemberResponse.Detail authenticate(String email, String password) {
        Member member = memberRepository.findByEmailAndPassword(email,password);
        return MemberResponse.Detail.of(member);
    }
}
