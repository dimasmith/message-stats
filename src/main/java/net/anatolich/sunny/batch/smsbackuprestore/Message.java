package net.anatolich.sunny.batch.smsbackuprestore;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name = "sms")
@XmlAccessorType(XmlAccessType.NONE)
public class Message {
    @XmlAttribute
    private String type;
}
