package net.anatolich.sunny.repository;

import net.anatolich.sunny.domain.Direction;
import net.anatolich.sunny.domain.SmsMessage;
import org.springframework.data.repository.CrudRepository;

public interface SmsMessageRepository extends CrudRepository<SmsMessage, Integer> {
    int countByDirection(Direction in);
}
