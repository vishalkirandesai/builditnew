package biz.buildit.repository;
import biz.buildit.main.WorksEngineer;
import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;

@RooJpaRepository(domainType = WorksEngineer.class)
public interface WorksEngineerRepository {
}
