package ca.sukhsingh.actions.on.google;

import ca.sukhsingh.actions.on.google.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by sukhsingh on 2017-08-29.
 */
@RunWith(MockitoJUnitRunner.class)
public class ApiAiAppTest {

    @InjectMocks
    private ApiAiApp app;

    /**
     * Describes the behavior for ApiAiApp tell method.
     */
    @Test
    public void test() {
        Response response = app.tell("hello");
        assertNotNull(response);
//        {
//            "speech": "hello",
//            "data": {
//                "google": {
//                    "expect_user_response": false,
//                    "is_ssml": false,
//                    "no_input_prompts": []
//                }
//            },
//            "contextOut": []
//        }
        assertSpeech(response, "hello");
        assertNotNull(response.getData());
        assertNotNull(response.getData().getGoogle());
        assertExpectUserResponseFalse(response);
        assertIsSsmlFalse(response);
        assertNotNull(response.getData().getGoogle().noInputPrompts);
        assertNotNull(response.getContextOut());

//        {
//            'speech': 'hello',
//            'data': {
//            'google': {
//                'expect_user_response': false,
//                'rich_response': {
//                    'items': [
//                    {
//                        'simple_response': {
//                        'text_to_speech': 'hello',
//                                'display_text': 'hi'
//                    }
//                    }
//            ],
//                    'suggestions': []
//                }
//            }
//        },
//            'contextOut': []
//        }

        response = app.tell("hello", "hi");
        assertNotNull(response);
        assertSpeech(response, "hello");
        assertExpectUserResponseFalse(response);
        assertNotNullRichResponse(response);
        assertTextToSpeech(response, "hello");
        assertDisplayText(response, "hi");


    }

    private void assertSpeech(Response response, String speech) {
        assertEquals(speech, response.getSpeech());
    }

    private void assertExpectUserResponseFalse(Response response) {
        assertEquals(false, response.getData().getGoogle().expectUserResponse);
    }

    private void assertIsSsmlFalse(Response response) {
        assertEquals(false, response.getData().getGoogle().isSsml);
    }

    private void assertNotNullRichResponse(Response response) {
        assertNotNull(response.getData().getGoogle().getRichResponse());
        assertNotNull(response.getData().getGoogle().getRichResponse().getItems());
        assertNotNull(response.getData().getGoogle().getRichResponse().getItems().get(0).getSimpleResponse());
        assertNotNull(response.getData().getGoogle().getRichResponse().getSuggestions());

    }

    private void assertTextToSpeech(Response response, String speech) {
        assertEquals(speech, response.getData().getGoogle().getRichResponse().getItems().get(0).getSimpleResponse().getTextToSpeech());
    }

    private void assertDisplayText(Response response, String displayText) {
        assertEquals(displayText, response.getData().getGoogle().getRichResponse().getItems().get(0).getSimpleResponse().getDisplayText());
    }

    private void assertSsmlText(Response response, String ssmlText) {
        assertEquals(ssmlText, response.getData().getGoogle().getRichResponse().getItems().get(0).getSimpleResponse().getSsml());
    }
}