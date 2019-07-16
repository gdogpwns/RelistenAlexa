package com.RelistenAlexa;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.audioplayer.PlayBehavior;

import java.util.Optional;

public class Playback {

    // TODO - place static Audioplayer handler classes inside here
    // This will fix the issue of needing to pass this object everywhere. Ezpz
    String bandName;
    Show show;
    Boolean firstLaunch = true;
    Boolean isPaused = true;
    long offsetInMilliseconds;
    String expectedPreviousToken;
    String token;


    public Playback(Show show, String bandName) {
        this.show = show;
        this.bandName = bandName;
    }

    public Optional<Response> startPlayback(HandlerInput handlerInput, PlayBehavior playBehavior) {
        isPaused = false;
        String speechText = null; // May cause error if speechText is null, can't remember

        if (firstLaunch) {

            speechText = String.format("Playing the %s show from %s", bandName, show.showDate);
            offsetInMilliseconds = 0;
            expectedPreviousToken = null;
            token = String.valueOf(show.currentSet.currentTrack.trackPosition);
            firstLaunch = false;
        }

        else {
            offsetInMilliseconds = 0;
            expectedPreviousToken = null;
            token = String.valueOf(show.currentSet.currentTrack.trackPosition);
        }

        return handlerInput.getResponseBuilder()
                .withSpeech(speechText)
                .addAudioPlayerPlayDirective(playBehavior, offsetInMilliseconds, expectedPreviousToken,
                        token, show.currentSet.currentTrack.url)
                .build();
    }

}
