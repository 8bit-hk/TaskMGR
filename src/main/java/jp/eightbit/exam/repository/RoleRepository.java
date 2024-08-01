package jp.eightbit.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.eightbit.exam.entity.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
