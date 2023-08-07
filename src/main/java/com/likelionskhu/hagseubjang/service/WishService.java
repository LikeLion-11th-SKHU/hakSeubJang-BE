package com.likelionskhu.hagseubjang.service;

import com.likelionskhu.hagseubjang.config.auth.dto.SessionUser;
import com.likelionskhu.hagseubjang.domain.lecture.Lecture;
import com.likelionskhu.hagseubjang.domain.lecture.LectureRepository;
import com.likelionskhu.hagseubjang.domain.user.User;
import com.likelionskhu.hagseubjang.domain.user.UserRepository;
import com.likelionskhu.hagseubjang.domain.wish.Wish;
import com.likelionskhu.hagseubjang.domain.wish.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Service
public class WishService {

    private WishRepository wishRepository;
    private UserRepository userRepository;
    private LectureRepository lectureRepository;

    private final HttpSession httpSession;

    @Transactional
    public void save(int lectureId) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        User user1 = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. email=" + user.getEmail()));

        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("해당 강좌가 없습니다. id=" + lectureId));

        Wish wish = new Wish();
        wish.setUser(user1);
        wish.setLecture(lecture);
        wishRepository.save(wish);
    }

    @Transactional
    public void delete(int id) {
        Wish wish = wishRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 찜이 없습니다. id=" + id));

        wishRepository.delete(wish);
    }

}
