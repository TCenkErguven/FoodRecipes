package com.foody.controller;

import com.foody.dto.request.AddPointRequestDto;
import com.foody.dto.request.RemovePointRequestDto;
import com.foody.dto.response.AddPointResponseDto;
import com.foody.repository.entity.Point;
import com.foody.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.foody.constants.ApiUrls.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(POINT)
@RequiredArgsConstructor
public class PointController {
    private final PointService pointService;

    @PostMapping(ADD_POINT + "/{token}")
    public ResponseEntity<AddPointResponseDto> addPoint(@PathVariable String token, @RequestBody @Valid AddPointRequestDto dto){
        return ResponseEntity.ok(pointService.addPoint(token, dto));
    }

    @DeleteMapping(REMOVE_POINT + "/{token}")
    public ResponseEntity<Boolean> removePoint(@PathVariable String token, @RequestBody @Valid RemovePointRequestDto dto){
        return ResponseEntity.ok(pointService.removePoint(token,dto));
    }

    @GetMapping(FIND_ALL)
    public ResponseEntity<List<Point>> findAll(){
        return ResponseEntity.ok(pointService.findAll());
    }


}
