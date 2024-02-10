package universe.universe.entitiy.friend;

import jakarta.persistence.*;
import lombok.Getter;
import universe.universe.entitiy.base.BaseEntity;
import universe.universe.entitiy.user.UserStatus;
@Entity
@Getter
public class Friend extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "friend_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    private FriendStatus friendStatus;
}
