package org.launchcode.mediareview.models.data;

import org.launchcode.mediareview.models.Media;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface MediaDao extends CrudRepository<Media, Integer> {
}
