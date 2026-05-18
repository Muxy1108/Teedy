package com.sismics.docs.core.util.action;

import com.sismics.docs.core.dao.dto.DocumentDto;
import jakarta.json.JsonObject;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Proxy;
import java.util.Collections;

/**
 * Test of document action utilities.
 */
public class TestAction {
    @Test
    public void testAddTagActionWithoutTag() {
        AddTagAction action = new AddTagAction();
        DocumentDto documentDto = new DocumentDto();

        action.execute(documentDto, jsonActionWithoutTag());
    }

    @Test
    public void testRemoveTagActionWithoutTag() {
        RemoveTagAction action = new RemoveTagAction();
        DocumentDto documentDto = new DocumentDto();

        action.execute(documentDto, jsonActionWithoutTag());
    }

    @Test
    public void testTagActionValidateWithoutTag() {
        AddTagAction action = new AddTagAction();

        try {
            action.validate(jsonActionWithoutTag());
            Assert.fail("Validation should fail when tag is missing");
        } catch (Exception e) {
            Assert.assertEquals("step.transitions.actions.tag is required", e.getMessage());
        }
    }

    @Test
    public void testProcessFilesActionValidate() {
        ProcessFilesAction action = new ProcessFilesAction();

        action.validate(jsonActionWithoutTag());
    }

    private JsonObject jsonActionWithoutTag() {
        return (JsonObject) Proxy.newProxyInstance(
                JsonObject.class.getClassLoader(),
                new Class<?>[] { JsonObject.class },
                (proxy, method, args) -> {
                    String methodName = method.getName();

                    if ("getString".equals(methodName)) {
                        return null;
                    }

                    if ("containsKey".equals(methodName)) {
                        return false;
                    }

                    if ("get".equals(methodName)) {
                        return null;
                    }

                    if ("isEmpty".equals(methodName)) {
                        return true;
                    }

                    if ("size".equals(methodName)) {
                        return 0;
                    }

                    if ("keySet".equals(methodName)) {
                        return Collections.emptySet();
                    }

                    if ("values".equals(methodName)) {
                        return Collections.emptyList();
                    }

                    if ("entrySet".equals(methodName)) {
                        return Collections.emptySet();
                    }

                    if ("toString".equals(methodName)) {
                        return "{}";
                    }

                    Class<?> returnType = method.getReturnType();

                    if (returnType.equals(boolean.class)) {
                        return false;
                    }

                    if (returnType.equals(int.class)) {
                        return 0;
                    }

                    return null;
                });
    }
}