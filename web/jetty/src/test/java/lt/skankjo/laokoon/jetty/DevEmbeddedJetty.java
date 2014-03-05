package lt.skankjo.laokoon.jetty;

import lt.skankjo.laokoon.jetty.EmbeddedJetty;

import java.lang.Override;
import java.lang.String;

public class DevEmbeddedJetty extends EmbeddedJetty {

    @Override
    protected String getPattern() {
        return ".*/classes/.*";
    }
}