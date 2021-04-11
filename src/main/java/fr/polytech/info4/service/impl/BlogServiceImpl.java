package fr.polytech.info4.service.impl;

import fr.polytech.info4.domain.Blog;
import fr.polytech.info4.repository.BlogRepository;
import fr.polytech.info4.service.BlogService;
import fr.polytech.info4.service.dto.BlogDTO;
import fr.polytech.info4.service.mapper.BlogMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Blog}.
 */
@Service
@Transactional
public class BlogServiceImpl implements BlogService {

    private final Logger log = LoggerFactory.getLogger(BlogServiceImpl.class);

    private final BlogRepository blogRepository;

    private final BlogMapper blogMapper;

    public BlogServiceImpl(BlogRepository blogRepository, BlogMapper blogMapper) {
        this.blogRepository = blogRepository;
        this.blogMapper = blogMapper;
    }

    @Override
    public BlogDTO save(BlogDTO blogDTO) {
        log.debug("Request to save Blog : {}", blogDTO);
        Blog blog = blogMapper.toEntity(blogDTO);
        blog = blogRepository.save(blog);
        return blogMapper.toDto(blog);
    }

    @Override
    public Optional<BlogDTO> partialUpdate(BlogDTO blogDTO) {
        log.debug("Request to partially update Blog : {}", blogDTO);

        return blogRepository
            .findById(blogDTO.getId())
            .map(
                existingBlog -> {
                    blogMapper.partialUpdate(existingBlog, blogDTO);
                    return existingBlog;
                }
            )
            .map(blogRepository::save)
            .map(blogMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BlogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Blogs");
        return blogRepository.findAll(pageable).map(blogMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BlogDTO> findOne(Long id) {
        log.debug("Request to get Blog : {}", id);
        return blogRepository.findById(id).map(blogMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Blog : {}", id);
        blogRepository.deleteById(id);
    }
}
