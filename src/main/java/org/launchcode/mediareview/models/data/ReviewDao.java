package org.launchcode.mediareview.models.data;

import org.launchcode.mediareview.models.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ReviewDao extends CrudRepository<Review, Integer> {
}
