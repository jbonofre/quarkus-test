package net.nanthrax.test.quarkus.opentelemetry;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

import java.util.logging.Logger;

class Launcher {

    @Inject
    private Tracer tracer;

    private static final Logger logger = Logger.getLogger(Launcher.class.getName());

    void onStart(@Observes StartupEvent event) {
        Span span = tracer.spanBuilder("My custom span")
                .setAttribute("my.attr", "attr")
                .setParent(Context.current().with(Span.current()))
                .setSpanKind(SpanKind.INTERNAL)
                .startSpan();
        span.addEvent("Starting ...");
        logger.info("Hello world !");
        span.addEvent("I'm done");
        span.end();
    }

}
