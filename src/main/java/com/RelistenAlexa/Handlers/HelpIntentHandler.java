package com.RelistenAlexa.Handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import static com.amazon.ask.request.Predicates.intentName;

import java.util.Optional;

public class HelpIntentHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("AMAZON.HelpIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String speechText = "Tell me a band and a date and I will play that show for you!";
        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("Help", speechText)
                .withReprompt(speechText)
                .build();
    }
}