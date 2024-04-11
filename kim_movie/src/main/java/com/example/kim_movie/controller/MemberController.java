package com.example.kim_movie.controller;

import com.example.kim_movie.application.MemberAppService;
import com.example.kim_movie.controller.request.MemberRequest;
import com.example.kim_movie.controller.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberAppService memberAppService;

    @PostMapping
    public MemberResponse.Detail createMember(@RequestBody MemberRequest.Create request){
        return memberAppService.createMember(request);
    }

    @PutMapping("/{id}")
    public MemberResponse.Detail updateMember(@PathVariable Long id, @RequestBody MemberRequest.Update request){
        return memberAppService.updatemember(id, request);
    }

    @GetMapping("/{id}")
    public MemberResponse.Detail retrieveDetail(@PathVariable Long id){
        return memberAppService.retrieveDetail(id);
    }

    @GetMapping
    public List<MemberResponse.Detail> retrieveList(){
        return memberAppService.retrieveList();
    }

    @DeleteMapping("/{id}")
    public Boolean deleteMember(@PathVariable Long id){
        return memberAppService.deleteMember(id);
    }

    @GetMapping("/follow/{fromId}/{toId}")
    public Boolean followMember(@PathVariable Long fromId, @PathVariable Long toId){
        return memberAppService.followMember(fromId, toId);
    }


}
