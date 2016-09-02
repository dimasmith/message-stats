package net.anatolich.sunny.domain;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Builder
@Data
@Entity
public class SmsMessage {

    @Id @GeneratedValue
    private Integer id;
    private Direction direction;

}
