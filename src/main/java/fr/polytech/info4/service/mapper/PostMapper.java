package fr.polytech.info4.service.mapper;

import fr.polytech.info4.domain.*;
import fr.polytech.info4.service.dto.PostDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Post} and its DTO {@link PostDTO}.
 */
@Mapper(componentModel = "spring", uses = { BlogMapper.class, TagMapper.class })
public interface PostMapper extends EntityMapper<PostDTO, Post> {
    @Mapping(target = "blog", source = "blog", qualifiedByName = "name")
    @Mapping(target = "tags", source = "tags", qualifiedByName = "nameSet")
    PostDTO toDto(Post s);

    @Mapping(target = "removeTag", ignore = true)
    Post toEntity(PostDTO postDTO);
}
