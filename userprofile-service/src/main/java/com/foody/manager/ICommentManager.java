package com.foody.manager;

import com.foody.dto.request.RemoveAllUserCommentsAndPointsRequestDto;
import com.foody.dto.request.UpdateCommentUsernameRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "http://localhost:9060/api/v1/comment", name = "userprofile-comment" ,decode404 = true)
public interface ICommentManager {

    @PutMapping("/update-comment-username")
    public ResponseEntity<Boolean> updateCommentUsername(@RequestBody UpdateCommentUsernameRequestDto dto);

    @DeleteMapping("/remove-all-user-comments-and-points")
    public ResponseEntity<Boolean> removeAllUserCommentsAndPoints(@RequestBody RemoveAllUserCommentsAndPointsRequestDto dto);

}
