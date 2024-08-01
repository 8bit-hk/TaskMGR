package jp.eightbit.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.eightbit.exam.entity.TaskState;

@Repository
public interface TaskStateRepository extends JpaRepository<TaskState, Long> {

}
