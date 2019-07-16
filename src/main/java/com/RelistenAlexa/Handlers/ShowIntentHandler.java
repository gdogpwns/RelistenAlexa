package com.RelistenAlexa.Handlers;

import com.RelistenAlexa.Playback;
import com.RelistenAlexa.Show;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.amazon.ask.model.interfaces.audioplayer.PlayBehavior;
import com.amazon.ask.model.slu.entityresolution.Resolution;
import com.amazon.ask.model.slu.entityresolution.Resolutions;
import com.amazon.ask.model.slu.entityresolution.ValueWrapper;
import com.amazon.ask.request.Predicates;
import com.amazon.ask.request.RequestHelper;

import java.util.Optional;

public class ShowIntentHandler implements IntentRequestHandler {

    @Override
    public boolean canHandle(HandlerInput handlerInput, IntentRequest intentRequest) {
        return handlerInput.matches(Predicates.intentName("ShowIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput, IntentRequest intentRequest) {
        RequestHelper requestHelper = RequestHelper.forHandlerInput(handlerInput);

        // From what I can find, Amazon makes you do ALL THIS to get the Slot ID. Stupid...
        Resolutions bandInputResolution = requestHelper.getSlot("BandInput").get().getResolutions();
        ValueWrapper bandInputResolutionValues = bandInputResolution.getResolutionsPerAuthority().get(0).getValues().get(0);
        String bandInputID = bandInputResolutionValues.getValue().getId();

        // bandInput and dateInput will always exist. This is handled with Alexa Developer Console.
        String dateInput = requestHelper.getSlotValue("DateInput").get();

        return createAudioStream(handlerInput, bandInputID, dateInput);
    }

    /**
     * Creates handlerInput to create an audio stream by calling
     * @param handlerInput sent from handle()
     * @param dateInput sent from handle()
     * @param bandInputID sent from handle()
     * @return Returns built handlerInput to handle()
     */
    public Optional<Response> createAudioStream(HandlerInput handlerInput,
                                          String bandInputID, String dateInput) {
        String speechText;
        Playback playback;

        String jsonUrl = String.format("https://api.relisten.net/api/v2/artists/%s/shows/%s", bandInputID, dateInput);

        Show show;
        try {
            show = new Show(jsonUrl);}
        catch (Exception e) {
            speechText = ("Sorry, but I cannot find that show right now.");
            return handlerInput.getResponseBuilder()
                    .withSpeech(speechText)
                    .withSimpleCard("Error", speechText)
                    .build();
        }

        playback = new Playback(show, bandInputID);
        return playback.startPlayback(handlerInput, PlayBehavior.fromValue("REPLACE_ALL"));
    }

}