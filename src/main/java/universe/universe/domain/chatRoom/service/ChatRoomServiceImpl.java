package universe.universe.domain.chatRoom.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import universe.universe.global.exception.Exception400;
import universe.universe.global.exception.Exception401;
import universe.universe.global.exception.Exception404;
import universe.universe.global.exception.Exception500;
import universe.universe.domain.chatRoom.dto.ChatRoomRequestDTO;
import universe.universe.domain.chatRoom.dto.ChatRoomResponseDTO;
import universe.universe.domain.chatRoom.entity.ChatRoom;
import universe.universe.domain.chatRoomRelation.entity.ChatRoomRelation;
import universe.universe.domain.user.entity.User;
import universe.universe.domain.chatRoomRelation.repository.ChatRoomRelationRepository;
import universe.universe.domain.chatRoom.repository.ChatRoomRepository;
import universe.universe.domain.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ChatRoomServiceImpl implements ChatRoomService {
    final private UserRepository userRepository;
    final private ChatRoomRepository chatRoomRepository;
    final private ChatRoomRelationRepository chatRoomRelationRepository;
    @Override
    public ChatRoomResponseDTO.ChatRoomCreateDTO create(String userEmail, ChatRoomRequestDTO.ChatRoomCreateDTO chatRoomCreateDTO) {
        try {
            List<ChatRoomRequestDTO.ChatRoomUserDTO> requestList = chatRoomCreateDTO.getUserList();
            List<ChatRoomResponseDTO.ChatRoomUserDTO> responseList = new ArrayList<>();
            Long findUserId = getUser_Email(userEmail).getId();

            checkRequestList(requestList, findUserId);

            ChatRoomRequestDTO.ChatRoomUserDTO chatRoomUserDTO = new ChatRoomRequestDTO.ChatRoomUserDTO();
            chatRoomUserDTO.setUserId(findUserId);
            requestList.add(chatRoomUserDTO);

            ChatRoom chatRoom = new ChatRoom();

            chatRoomRepository.save(chatRoom);

            addResponseList(requestList, responseList, chatRoom);
            ChatRoomResponseDTO.ChatRoomCreateDTO result = new ChatRoomResponseDTO.ChatRoomCreateDTO(chatRoom.getId(), responseList);
            return result;
        }
        catch (Exception e) {
            throw new Exception500("chatRoom create fail : " + e.getMessage());
        }
    }

    @Override
    public void delete(String userEmail, Long chatRoomId) {
        try {
            User findUser = getUser_Email(userEmail);
            ChatRoom findChatRoom = getChatRoom_Id(chatRoomId);
            ChatRoomRelation findChatRoomRelation = getChatRoomRelation(findUser, findChatRoom);
            chatRoomRelationRepository.delete(findChatRoomRelation);
        } catch (Exception e) {
            throw new Exception500("chatRoom delete fail : " + e.getMessage());
        }
    }

    @Override
    public ChatRoomResponseDTO.ChatRoomFindAllDTO findAll(String userEmail) {
        try {
            User findUser = getUser_Email(userEmail);
            ChatRoomResponseDTO.ChatRoomFindAllDTO result = chatRoomRelationRepository.ChatRoomRelationFindAll(findUser.getId());
            return result;
        } catch (Exception e) {
            throw new Exception500("chatRoom findAll fail : " + e.getMessage());
        }
    }


    private User getUser_Email(String userEmail) {
        User findUser = userRepository.findByUserEmail(userEmail);
        if(findUser == null) {
            throw new Exception400("userEmail", "해당 유저를 찾을 수 없습니다.");
        }
        return findUser;
    }

    private User getUser_Id(Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        if(!findUser.isPresent()) {
            throw new Exception400("userId", "해당 유저를 찾을 수 없습니다.");
        }
        return findUser.get();
    }

    private ChatRoom getChatRoom_Id(Long chatRoomId) {
        Optional<ChatRoom> findChatRoom = chatRoomRepository.findById(chatRoomId);
        if(!findChatRoom.isPresent()) {
            throw new Exception400("chatRoom", "해당 채팅방을 찾을 수 없습니다.");
        }
        return findChatRoom.get();
    }
    private ChatRoomRelation getChatRoomRelation(User findUser, ChatRoom findChatRoom) {
        Optional<ChatRoomRelation> findChatRoomRelation = chatRoomRelationRepository.findByUserAndChatRoom(findUser, findChatRoom);
        if(!findChatRoomRelation.isPresent()) {
            throw new Exception400("chatRoomRelation", "해당 관계를 찾을 수 없습니다.");
        }
        return findChatRoomRelation.get();
    }

    private void addResponseList(List<ChatRoomRequestDTO.ChatRoomUserDTO> requestList, List<ChatRoomResponseDTO.ChatRoomUserDTO> responseList, ChatRoom chatRoom) {
        for(ChatRoomRequestDTO.ChatRoomUserDTO user : requestList) {
            User findUser = getUser_Id(user.getUserId());
            ChatRoomRelation chatRoomRelation = chatRoomRelationRepository.save(new ChatRoomRelation(findUser, chatRoom));
            responseList.add(new ChatRoomResponseDTO.ChatRoomUserDTO(chatRoomRelation));
        }
    }

    private static void checkRequestList(List<ChatRoomRequestDTO.ChatRoomUserDTO> requestList, Long findUserId) {
        for(ChatRoomRequestDTO.ChatRoomUserDTO user : requestList) {
            if(user.getUserId() == findUserId) {
                throw new Exception400("requestList", "본인에게 채팅은 불가합니다.");
            }
        }
    }
}
