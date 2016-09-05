package net.anatolich.sunny.batch.smsbackuprestore;

import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;

@NoArgsConstructor
@XmlRootElement(name = "sms")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Message {
    private static final String IN = "1";
    private static final String OUT = "2";

    private String type = IN;
    private long date = System.currentTimeMillis();

    public Message(String type, long date) {
        assertCorrectType(type);
        this.type = type;
        this.date = date;
    }

    private void assertCorrectType(String type) {
        if (type == null) {
            throw new IllegalArgumentException("Message type must not be null");
        }
        if (!type.equals(IN) && !type.equals(OUT)) {
            throw new IllegalArgumentException(
                    String.format("Message type value %s is incorrect. Only supported values are %s",
                            type, Arrays.toString(new String[]{IN, OUT})));
        }

    }

    @XmlAttribute
    public String getType() {
        return this.type;
    }

    @XmlAttribute
    public long getDate() {
        return this.date;
    }

    public void setType(String type) {
        assertCorrectType(type);
        this.type = type;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Message)) return false;
        final Message other = (Message) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$type = this.getType();
        final Object other$type = other.getType();
        if (this$type == null ? other$type != null : !this$type.equals(other$type)) return false;
        if (this.getDate() != other.getDate()) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $type = this.getType();
        result = result * PRIME + ($type == null ? 43 : $type.hashCode());
        final long $date = this.getDate();
        result = result * PRIME + (int) ($date >>> 32 ^ $date);
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof Message;
    }

    public String toString() {
        return "net.anatolich.sunny.batch.smsbackuprestore.Message(type=" + this.getType() + ", date=" + this.getDate() + ")";
    }
}
