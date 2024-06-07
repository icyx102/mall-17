package priv.jesse.mall.service.impl;

import jakarta.annotation.Resource;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import priv.jesse.mall.dao.TagDao;
import priv.jesse.mall.entity.Tag;
import priv.jesse.mall.service.TagService;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Resource
    private TagDao tagDao;

    @Override
    public Tag findById(Integer id) {
        return tagDao.getReferenceById(id);
    }

    @Override
    public List<Tag> findAll(Example<Tag> example) {
        return tagDao.findAll(example);
    }

    @Override
    public void create(Tag tag) {
        tagDao.save(tag);
    }

    @Override
    public void updateById(Tag tag) {
        tagDao.save(tag);
    }

    @Override
    public boolean deleteById(Integer id) {
        tagDao.deleteById(id);
        return true;
    }
}
