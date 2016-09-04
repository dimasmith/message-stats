package net.anatolich.sunny.batch.smsbackuprestore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "sms")
@XmlAccessorType(XmlAccessType.NONE)
public class Message {
    @XmlAttribute
    private String type;
    @XmlAttribute
    private long date;
}
