package net.anatolich.sunny.batch.smsbackuprestore;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;

public class MessageReader implements ItemStreamReader<Message> {

    private final StaxEventItemReader<Message> reader;
    @Value("#{jobParameters[url]}")
    private String url;

    public MessageReader() {
        this.reader = createReader();
    }

    @PostConstruct
    public void populateResourceFromJobParameter() {
        try {
            final UrlResource resource = new UrlResource(url);
            setResource(resource);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setResource(Resource resource) {
        reader.setResource(resource);
    }

    @Override
    public Message read() throws Exception {
        return reader.read();
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        this.reader.open(executionContext);
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        this.reader.update(executionContext);
    }

    @Override
    public void close() throws ItemStreamException {
        this.reader.close();
    }

    private StaxEventItemReader<Message> createReader() {
        final Jaxb2Marshaller unmarshaler = new Jaxb2Marshaller();
        unmarshaler.setClassesToBeBound(Message.class);

        final StaxEventItemReader<Message> reader = new StaxEventItemReader<>();
        reader.setFragmentRootElementName("sms");
        reader.setUnmarshaller(unmarshaler);
        return reader;
    }
}
