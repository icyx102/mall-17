package priv.jesse.mall.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import priv.jesse.mall.entity.Tag;

public interface TagDao extends JpaRepository<Tag, Integer> {
}
