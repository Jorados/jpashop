package jpabook.jpashop.controller;

import jpabook.jpashop.NoAuthorizationEx;
import jpabook.jpashop.controller.form.MemberForm;
import jpabook.jpashop.domain.embedded.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;


    @ExceptionHandler(NoAuthorizationEx.class)  //에러 처리
    public String HandlerException(Exception ex, HttpServletRequest request , Model model){

        model.addAttribute("exMessage",ex.getMessage());
        model.addAttribute("queryParam",request.getQueryString());

        return "error/NoAuthorizationEx-redirect";
    }

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @GetMapping("/members/{memberId}/myPage")
    public String MyPageForm(@PathVariable("memberId") Long memberId,Model model){
        Member findMember = memberService.findOne(memberId);
        model.addAttribute("findMember",findMember);
        return "members/myPageForm";
    }

    @PostMapping("/members/{memberId}/myPage")
    public String updateMember(@PathVariable Long memberId, @ModelAttribute("form") MemberForm form) {
        memberService.updateMember(memberId, form.getName(), form.getLoginId(), form.getPassword());
        return "redirect:/";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {

        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);
        member.setLoginId(form.getLoginId());
        member.setPassword(form.getPassword());

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

    @GetMapping("/members/{memberId}/delete")
    public String deleteForm(@PathVariable ("memberId") Long memberId, Model model){
        Member findMember = memberService.findOne(memberId);
        model.addAttribute("findMember",findMember);
        return "members/deletePage";
    }
    @PostMapping("/members/{memberId}/delete")
    public String delete(@PathVariable Long memberId, HttpServletRequest request, @SessionAttribute(name = "loginMember")Member loginMember){

        log.info("memberId" + memberId);
        log.info("삭제하려는 멤버 아이디" + loginMember.getId());

        Member findMember = memberService.findOne(memberId);
        if(!loginMember.getId().equals(findMember.getId()) ){
            throw new NoAuthorizationEx("삭제 권한이 없음 ");
        }

        //initDB 정보는 삭제x
        memberService.delete(memberId);

        //세션 삭제
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/";
    }

}
