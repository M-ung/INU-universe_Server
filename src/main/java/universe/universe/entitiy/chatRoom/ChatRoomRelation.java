package universe.universe.entitiy.chatRoom;

import jakarta.persistence.*;
import lombok.Getter;
import universe.universe.entitiy.user.User;

@Entity
@Getter
public class ChatRoomRelation {
    @Id
    @GeneratedValue
    @Column(name = "chatroom_relationship_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "chatroom_id")
    private ChatRoom chatRoom;

    public ChatRoomRelation(User user, ChatRoom chatRoom) {
        this.user = user;
        this.chatRoom = chatRoom;
    }
}
