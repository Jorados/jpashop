package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @GetMapping("/members/{memberId}/myPage")
    public String MyPageForm(@PathVariable("memberId") Long memberId,Model model){
        Member findMember = memberService.findOne(memberId);

        MemberForm form = new MemberForm();
        form.setName(findMember.getName());
        form.setLoginId(findMember.getLoginId());
        form.setPassword(findMember.getPassword());
        form.setAddress(findMember.getAddress());

        model.addAttribute("form", form);
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

}
