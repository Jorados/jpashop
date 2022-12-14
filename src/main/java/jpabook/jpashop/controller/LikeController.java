package jpabook.jpashop.controller;


import jpabook.jpashop.domain.Board;
import jpabook.jpashop.domain.Item;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.BoardService;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.LikesService;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LikeController {

    private final LikesService likesService;
    private final MemberService memberService;
    private final BoardService boardService;
    private final ItemService itemService;

    @PostMapping("/like")
    @ResponseBody
    public int like(@SessionAttribute(name = "loginMember", required = false) Member loginMember,
                    @RequestParam("likeCheck") int likeCheck,
                    @RequestParam("boardId") Long boardId) {

        log.info("likeCheck = " + likeCheck);   //로그찍고
        log.info("boardId = " + boardId);

        if (likeCheck == 1) {
            log.info("좋아요가 눌러져 있는상태 .취소 로직 가동");
            likesService.unlike(loginMember.getId(), boardId);
        } else if (likeCheck == 0) {
            log.info("좋아요가 안눌러져 있는 상태. 누르는 로직 가동");
            likesService.like(loginMember.getId(), boardId);
        }

        Board findBoard = boardService.findOne(boardId);
        int updateLikeCount = findBoard.getLikeCount();
        return updateLikeCount;
    }

    @PostMapping("/like2")
    @ResponseBody
    public int like2(@SessionAttribute(name = "loginMember", required = false) Member loginMember,
                    @RequestParam("likeCheck") int likeCheck,
                    @RequestParam("itemId") Long itemId) {

        log.info("likeCheck = " + likeCheck);   //로그찍고
        log.info("itemId = " + itemId);

        if (likeCheck == 1) {
            log.info("좋아요가 눌러져 있는상태 .취소 로직 가동");
            likesService.unlike2(loginMember.getId(), itemId);
        } else if (likeCheck == 0) {
            log.info("좋아요가 안눌러져 있는 상태. 누르는 로직 가동");
            likesService.like2(loginMember.getId(), itemId);
        }

        Item findItem = itemService.findOne(itemId);
        int updateLikeCount = findItem.getLikeCount2();
        return updateLikeCount;
    }
}
