package net.anatolich.sunny.domain;

import lombok.Value;

@Value
public class SenderStats {

    private final int incoming;
    private final int outgoing;

}
