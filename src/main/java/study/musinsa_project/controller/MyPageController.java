package study.musinsa_project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import study.musinsa_project.dto.*;
import study.musinsa_project.service.MyPageService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "마이페이지 컨트롤러", description = "마이페이지 요청 API")
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/myPage/{userId}")
    @Operation(summary = "해당 유저 마이페이지 조회 API", description = "해당 유저 userId(primary key)값을 넘겨 주세요.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "해당 유저가 존재 합니다.",
                    content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = MyPageUserResponse.class))),
            @ApiResponse(responseCode = "400", description = "해당 유저가 존재하지 않습니다.",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMyPageResponse.class))),
            @ApiResponse(responseCode = "500", description = "해당 유저를 찾아오지 못했습니다.",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMyPageResponse.class))),
    })
    public ResponseEntity<MyPageUserResponse> myPage(@PathVariable int userId){
        return ResponseEntity.ok().body(myPageService.selectMyPage(userId));
    }

    @Operation(summary = "해당 유저 마이페이지 상세 API", description = "해당 유저 userId(primary key)값을 넘겨 주세요.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "해당 유저 정보를 출력 합니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MyPageUserDetailResponse.class))),
            @ApiResponse(responseCode = "400", description = "해당 유저가 정보가 존재하지 않습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMyPageResponse.class))),
            @ApiResponse(responseCode = "500", description = "해당 유저를 정보를 찾아오지 못했습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMyPageResponse.class))),
    })
    @GetMapping("/myPageDetail/{userId}")
    public ResponseEntity<MyPageUserDetailResponse> myPageDetail(@PathVariable int userId){
        return ResponseEntity.ok(myPageService.myPageUserDetail(userId));
    }

    @Operation(summary = "마이페이지 정보 수정 API", description = "유저 primary key 값과 수정된 정보를 넘겨 주세요.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "변경된 유저 정보를 출력 합니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MyPageUserDetailResponse.class))),
            @ApiResponse(responseCode = "400", description = "해당 유저가 정보가 존재하지 않습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMyPageResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 오류로 정보변경 을 실패 했습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMyPageResponse.class))),
    })
    @PostMapping("/myPageUserUpdate/{userId}")
    public ResponseEntity<MyPageUserDetailResponse> myPageUserUpdate(
            @PathVariable int userId, @RequestBody MyPageUserUpdateRequest myPageUserUpdateRequest){
        return myPageService.myPageUserUpdate(userId,myPageUserUpdateRequest);
    }

    @Operation(summary = "마이페이지 장바구니 조회 API", description = "해당 유저 userId(primary key)값을 넘겨 주세요.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "해당 유저 장바구니 정보를 출력 합니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MyPageCartResponse.class))),
            @ApiResponse(responseCode = "400", description = "해당 유저가 정보가 존재하지 않습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMyPageResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 오류로 장바구니 내역 조회에 실패 했습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMyPageResponse.class))),
    })
    @GetMapping("/myPageCart/{userId}")
    public ResponseEntity<MyPageCartResponse> myPageCart(@PathVariable int userId){
        return ResponseEntity.ok().body(myPageService.selectMyPageCart(userId));
    }

    @Operation(summary = "마이페이지 이미지 수정 API", description = "유저 primary key 값과 이미지 를 form-data 로 넘겨 주세요.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "유저 이미지 변경에 성공 했습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MyPageUserDetailResponse.class))),
            @ApiResponse(responseCode = "400", description = "해당 유저가 정보가 존재하지 않습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMyPageResponse.class))),
            @ApiResponse(responseCode = "500", description = "\"jpg\", \"jpeg\", \"png\", \"gif\" 형식이 맞는지 확인 해 주세요.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMyPageResponse.class))),
    })
    @PutMapping("/myPageImageUpload/{userId}")
    public ResponseEntity<?> myPageImageUpload(@RequestPart(value = "image", required = false) MultipartFile image, @PathVariable int userId){
        return ResponseEntity.ok(myPageService.upload(image,userId));
    }
}
