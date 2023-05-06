package ru.wb.bot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.wb.bot.entity.PriceEntity;

import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<PriceEntity, Long> {
    PriceEntity getFirstByProductIdOrderByDateDesc(Long id);

    List<PriceEntity> getPriceEntityByProductIdOrderByDateDesc(Long id);
}
