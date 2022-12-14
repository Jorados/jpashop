package jpabook.jpashop.service;

import jpabook.jpashop.domain.Board;
import jpabook.jpashop.domain.Item;
import jpabook.jpashop.domain.Likes;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.BoardRepository;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.LikesRepository;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final LikesRepository likesRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Likes like(Long memberId,Long boardId){
        Member findMember = memberRepository.findOne(memberId);
        Board findBoard = boardRepository.findOne(boardId);

        if(isNotAlreadyLike(findMember,findBoard)){
            Likes like = Likes.createLike(findMember, findBoard);
            Likes newLike = likesRepository.like(like);
            findBoard.addLikeCount();

            return newLike;
        }else{
            throw new IllegalStateException("이미 좋아요 한 게시글입니다.");
        }
    }

    @Transactional
    public Likes like2(Long memberId,Long itemId){
        Member findMember = memberRepository.findOne(memberId);
        Item findItem = itemRepository.findOne(itemId);

        if(isNotAlreadyLike2(findMember,findItem)){
            Likes like = Likes.createLike2(findMember, findItem);
            Likes newLike = likesRepository.like2(like);
            findItem.addLikeCount2();

            return newLike;
        }else{
            throw new IllegalStateException("이미 좋아요 한 게시글입니다.");
        }
    }

    @Transactional
    public void unlike(Long memberId,Long boardId){
        Board findBoard = boardRepository.findOne(boardId);
        likesRepository.unlike(memberId,boardId,findBoard);
        findBoard.minusLikeCount();
    }

    @Transactional
    public void unlike2(Long memberId,Long itemId){
        Item findItem = itemRepository.findOne(itemId);
        likesRepository.unlike2(memberId,itemId,findItem);
        findItem.minusLikeCount2();
    }


    private boolean isNotAlreadyLike(Member findMember, Board findBoard) {
        Likes findLike = likesRepository.findLike(findMember, findBoard);
        if(findLike == null){
            return true;
        }else{
            return false;
        }
    }
    private boolean isNotAlreadyLike2(Member findMember, Item findItem) {
        Likes findLike = likesRepository.findLike2(findMember, findItem);
        if(findLike == null){
            return true;
        }else{
            return false;
        }
    }

    //내가 좋아요 눌렀는지 안눌렀는지
    public Map<Long, Integer> getLikeBoardId(Long memberId, List<Board> boards){
        List<Board> likesBoard = boardRepository.findLikesBoard(memberId);
        Map<Long, Integer> myLikesBoardId = new HashMap<>();

        for(Board board : boards){
            for(Board likeBoard : likesBoard){
                if(board.getId() == likeBoard.getId()){
                    myLikesBoardId.put(board.getId(),1);
                }else{
                    myLikesBoardId.put(board.getId(),0);
                }
            }
        }
        return myLikesBoardId;
    }

    public Map<Long, Integer> getLikeItemId(Long memberId, List<Item> items){
        List<Item> likesItem = itemRepository.findLikesItem(memberId);
        Map<Long, Integer> myLikesItemId = new HashMap<>();

        for(Item item : items){
            for(Item likeItem : likesItem){
                if(item.getId() == likeItem.getId()){
                    myLikesItemId.put(item.getId(),1);
                }else{
                    myLikesItemId.put(item.getId(),0);
                }
            }
        }
        return myLikesItemId;
    }

}
