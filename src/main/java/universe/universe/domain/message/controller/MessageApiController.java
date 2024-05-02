package universe.universe.domain.message.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import universe.universe.global.common.exception.Exception500;
import universe.universe.global.common.reponse.ApiResponse;
import universe.universe.domain.message.dto.MessageRequestDTO;
import universe.universe.domain.message.dto.MessageResponseDTO;
import universe.universe.domain.user.entity.User;
import universe.universe.global.auth.service.AuthenticationService;
import universe.universe.domain.message.service.MessageServiceImpl;

@RestController
@RequestMapping("/api/v1/user/message")
@RequiredArgsConstructor
@Slf4j
public class MessageApiController {
    final private MessageServiceImpl messageService;
    final private AuthenticationService authenticationService;

    // 메세지 저장
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody MessageRequestDTO.MessageSaveDTO messageSaveDTO) {
        try {
            log.info("[MessageApiController] save");
            String userEmail = getUserEmail();
            MessageResponseDTO.MessageSaveDTO result = messageService.save(messageSaveDTO, userEmail);
            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "[SUCCESS] MessageApiController save", result));
        } catch (Exception500 e) {
            log.info("[Exception500] MessageApiController save");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage()));
        }
    }

    // 메세지 삭제
    @PostMapping("/delete/{messageId}")
    public ResponseEntity<?> delete(@PathVariable Long messageId) {
        try {
            log.info("[MessageApiController] delete");
            String userEmail = getUserEmail();
            messageService.delete(userEmail, messageId);
            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "[SUCCESS] MessageApiController delete"));
        } catch (Exception500 e) {
            log.info("[Exception500] MessageApiController delete");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage()));
        }
    }

    // 메세지 전체 조회
    @GetMapping("/findAll/{chatRoomId}")
    public ResponseEntity<?> findAll(@PathVariable Long chatRoomId) {
        try {
            log.info("[MessageApiController] findAll");
            String userEmail = getUserEmail();
            MessageResponseDTO.MessageFindAllDTO result = messageService.findAll(userEmail, chatRoomId);
            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "[SUCCESS] MessageApiController findAll", result));
        } catch (Exception500 e) {
            log.info("[Exception500] MessageApiController findAll");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage()));
        }
    }


    private String getUserEmail() {
        User user = authenticationService.getCurrentAuthenticatedUser();
        String userEmail = user.getUserEmail();
        return userEmail;
    }
}
