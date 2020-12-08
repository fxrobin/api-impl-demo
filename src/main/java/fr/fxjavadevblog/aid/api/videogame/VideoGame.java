package fr.fxjavadevblog.aid.api.videogame;

import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import fr.fxjavadevblog.aid.api.genre.Genre;
import fr.fxjavadevblog.aid.utils.InjectUUID;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@SuppressWarnings({ "serial"})
@EqualsAndHashCode(of = "id")
@ToString(of = { "id", "name", "genre" })
@NoArgsConstructor(access = AccessLevel.PACKAGE)
// CDI Annotation
@Dependent

// JPA Annotation
@Entity
@Table(name = "VIDEO_GAME")
public class VideoGame implements Serializable
{

    @Id
    @Getter
    @Column(length = 36)
    @Inject
    @InjectUUID
    String id;

    @Getter
    @Setter
    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "GENRE", nullable = false)
    private Genre genre;

    @Version
    @Getter
    @Column(name = "VERSION")
    private Long version;
}
