package priv.jesse.mall.service;

import org.springframework.data.domain.Example;
import priv.jesse.mall.entity.Tag;

import java.util.List;

public interface TagService {

    Tag findById(Integer id);

    List<Tag> findAll(Example<Tag> example);

    void create(Tag tag);

    void updateById(Tag tag);

    boolean deleteById(Integer id);
}
