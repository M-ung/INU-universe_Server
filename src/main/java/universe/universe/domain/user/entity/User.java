package universe.universe.domain.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;
import universe.universe.global.common.BaseEntity;
import universe.universe.domain.location.entity.Location;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter
public class User extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;
    private String role = "ROLE_USER";
    private String userEmail;
    private String userPassword;
    private String userName;
    private String userImg;
    private String userEmotion = "1";

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus = UserStatus.NOT_SCHOOL;

    @LastModifiedDate
    private LocalDateTime schoolDate; // 등교 시간

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Location location;

    /** ======================== 메소드 ======================== **/
    public void updateRole(String role) {
        this.role = role;
    }
    public void updateUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }
    public void updateUserEmotion(String userEmotion) { this.userEmotion = userEmotion; }
    public void updateUserImg(String userImg) {
        this.userImg = userImg;
    }

    public List<String> getRoleList() {
        if (this.role.length() > 0) {
            return Arrays.asList(this.role.split(","));
        }
        return new ArrayList<>();
    }

    /** ======================== 생성자 ======================== **/
    protected User() {
    }

    public User(String userEmail, String userPassword, String userName) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userName = userName;
        this.location = new Location();
    }

    @Builder
    public User(String userEmail, String userName, String userPassword, String userImg, String role) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userImg = userImg;
        this.role = role;
        this.location = new Location();
    }
    @Builder
    public User(String userEmail, String userName, String userPassword, String role, String provider, String providerId) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPassword = userPassword;
        this.role = role;
        this.location = new Location();
    }
}
