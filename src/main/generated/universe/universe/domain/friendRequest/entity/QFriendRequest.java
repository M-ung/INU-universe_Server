package universe.universe.domain.friendRequest.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFriendRequest is a Querydsl query type for FriendRequest
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFriendRequest extends EntityPathBase<FriendRequest> {

    private static final long serialVersionUID = -99489235L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFriendRequest friendRequest = new QFriendRequest("friendRequest");

    public final universe.universe.global.common.QBaseEntity _super = new universe.universe.global.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdTime = _super.createdTime;

    public final universe.universe.domain.user.entity.QUser fromUser;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final universe.universe.domain.user.entity.QUser toUser;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedTime = _super.updatedTime;

    public QFriendRequest(String variable) {
        this(FriendRequest.class, forVariable(variable), INITS);
    }

    public QFriendRequest(Path<? extends FriendRequest> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFriendRequest(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFriendRequest(PathMetadata metadata, PathInits inits) {
        this(FriendRequest.class, metadata, inits);
    }

    public QFriendRequest(Class<? extends FriendRequest> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.fromUser = inits.isInitialized("fromUser") ? new universe.universe.domain.user.entity.QUser(forProperty("fromUser"), inits.get("fromUser")) : null;
        this.toUser = inits.isInitialized("toUser") ? new universe.universe.domain.user.entity.QUser(forProperty("toUser"), inits.get("toUser")) : null;
    }

}

