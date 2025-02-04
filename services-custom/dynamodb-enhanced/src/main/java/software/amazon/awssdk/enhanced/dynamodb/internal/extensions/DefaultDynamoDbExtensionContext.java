/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package software.amazon.awssdk.enhanced.dynamodb.internal.extensions;

import java.util.Map;
import java.util.Objects;
import software.amazon.awssdk.annotations.SdkInternalApi;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbExtensionContext;
import software.amazon.awssdk.enhanced.dynamodb.OperationContext;
import software.amazon.awssdk.enhanced.dynamodb.TableMetadata;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.internal.operations.OperationName;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

/**
 * An SDK-internal implementation of {@link DynamoDbExtensionContext.BeforeWrite} and
 * {@link DynamoDbExtensionContext.AfterRead}.
 */
@SdkInternalApi
public final class DefaultDynamoDbExtensionContext implements DynamoDbExtensionContext.BeforeWrite,
                                                              DynamoDbExtensionContext.AfterRead {
    private final Map<String, AttributeValue> items;
    private final OperationContext operationContext;
    private final TableMetadata tableMetadata;
    private final TableSchema<?> tableSchema;
    private final OperationName operationName;

    private DefaultDynamoDbExtensionContext(Builder builder) {
        this.items = builder.items;
        this.operationContext = builder.operationContext;
        this.tableMetadata = builder.tableMetadata;
        this.tableSchema = builder.tableSchema;
        this.operationName = builder.operationName != null ? builder.operationName : OperationName.NONE;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public Map<String, AttributeValue> items() {
        return items;
    }

    @Override
    public OperationContext operationContext() {
        return operationContext;
    }

    @Override
    public TableMetadata tableMetadata() {
        return tableMetadata;
    }

    @Override
    public TableSchema<?> tableSchema() {
        return tableSchema;
    }

    @Override
    public OperationName operationName() {
        return operationName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DefaultDynamoDbExtensionContext that = (DefaultDynamoDbExtensionContext) o;

        if (!Objects.equals(items, that.items)) {
            return false;
        }
        if (!Objects.equals(operationContext, that.operationContext)) {
            return false;
        }
        if (!Objects.equals(tableMetadata, that.tableMetadata)) {
            return false;
        }
        if (!Objects.equals(tableSchema, that.tableSchema)) {
            return false;
        }
        return Objects.equals(operationName, that.operationName);
    }

    @Override
    public int hashCode() {
        int result = items != null ? items.hashCode() : 0;
        result = 31 * result + (operationContext != null ? operationContext.hashCode() : 0);
        result = 31 * result + (tableMetadata != null ? tableMetadata.hashCode() : 0);
        result = 31 * result + (tableSchema != null ? tableSchema.hashCode() : 0);
        result = 31 * result + (operationName != null ? operationName.hashCode() : 0);
        return result;
    }

    public static final class Builder {
        private Map<String, AttributeValue> items;
        private OperationContext operationContext;
        private TableMetadata tableMetadata;
        private TableSchema<?> tableSchema;
        private OperationName operationName;

        public Builder items(Map<String, AttributeValue> item) {
            this.items = item;
            return this;
        }

        public Builder operationContext(OperationContext operationContext) {
            this.operationContext = operationContext;
            return this;
        }

        public Builder tableMetadata(TableMetadata tableMetadata) {
            this.tableMetadata = tableMetadata;
            return this;
        }

        public Builder tableSchema(TableSchema<?> tableSchema) {
            this.tableSchema = tableSchema;
            return this;
        }

        public Builder operationName(OperationName operationName) {
            this.operationName = operationName;
            return this;
        }

        public DefaultDynamoDbExtensionContext build() {
            return new DefaultDynamoDbExtensionContext(this);
        }
    }
}
