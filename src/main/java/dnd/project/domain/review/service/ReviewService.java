package dnd.project.domain.review.service;

import dnd.project.domain.lecture.entity.Lecture;
import dnd.project.domain.lecture.repository.LectureRepository;
import dnd.project.domain.review.entity.Review;
import dnd.project.domain.review.repository.ReviewRepository;
import dnd.project.domain.review.request.ReviewServiceRequest;
import dnd.project.domain.review.response.ReviewResponse;
import dnd.project.domain.user.entity.Users;
import dnd.project.domain.user.repository.UserRepository;
import dnd.project.global.common.Result;
import dnd.project.global.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static dnd.project.global.common.Result.ALREADY_CREATED_REVIEW;
import static dnd.project.global.common.Result.NOT_FOUND_LECTURE;
import static java.lang.Boolean.TRUE;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserRepository userRepository;
    private final LectureRepository lectureRepository;
    private final ReviewRepository reviewRepository;

    public ReviewResponse.Create createReview(ReviewServiceRequest.Create request, Long userId) {
        Users user = getUser(userId);
        Lecture lecture = getLecture(request.getLectureId());
        Boolean isCreated = reviewRepository.existsByLectureAndUser(lecture, user);

        if (isCreated.equals(TRUE)) {
            throw new CustomException(ALREADY_CREATED_REVIEW);
        }

        Review review = reviewRepository.save(
                toEntityReview(request.getScore(), request.getTags(), request.getContent(), user, lecture)
        );

        return ReviewResponse.Create.response(review, lecture, user);
    }

    private static Review toEntityReview(
            Integer score, String tags, Optional<String> optionalContent,
            Users user, Lecture lecture
    ) {
        return Review.builder()
                .user(user)
                .lecture(lecture)
                .score(score)
                .tags(tags)
                .content(optionalContent.orElse(""))
                .build();
    }

    // methode
    private Lecture getLecture(Long lectureId) {
        return lectureRepository.findById(lectureId).orElseThrow(
                () -> new CustomException(NOT_FOUND_LECTURE)
        );
    }

    private Users getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new CustomException(Result.NOT_FOUND_USER)
        );
    }

}
