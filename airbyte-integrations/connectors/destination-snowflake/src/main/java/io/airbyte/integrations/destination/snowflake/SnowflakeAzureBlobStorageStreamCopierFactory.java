/*
 * Copyright (c) 2021 Airbyte, Inc., all rights reserved.
 */

package io.airbyte.integrations.destination.snowflake;
import com.azure.storage.blob.specialized.AppendBlobClient;;

import com.azure.storage.blob.specialized.SpecializedBlobClientBuilder;
import io.airbyte.db.jdbc.JdbcDatabase;
import io.airbyte.integrations.destination.ExtendedNameTransformer;
import io.airbyte.integrations.destination.jdbc.SqlOperations;
import io.airbyte.integrations.destination.jdbc.StagingFilenameGenerator;
import io.airbyte.integrations.destination.jdbc.constants.GlobalDataSizeConstants;
import io.airbyte.integrations.destination.jdbc.copy.StreamCopier;
import io.airbyte.integrations.destination.jdbc.copy.azure.AzureBlobStorageConfig;
import io.airbyte.integrations.destination.jdbc.copy.azure.AzureBlobStorageStreamCopierFactory;
import io.airbyte.protocol.models.DestinationSyncMode;

public class SnowflakeAzureBlobStorageStreamCopierFactory extends AzureBlobStorageStreamCopierFactory {

  @Override
  public StreamCopier create(String stagingFolder,
      DestinationSyncMode syncMode,
      String schema,
      String streamName,
      SpecializedBlobClientBuilder specializedBlobClientBuilder,
      JdbcDatabase db,
      AzureBlobStorageConfig azureBlobConfig,
      ExtendedNameTransformer nameTransformer,
      SqlOperations sqlOperations)
      throws Exception {
    return new SnowflakeAzureBlobStorageStreamCopier(stagingFolder,
        syncMode,
        schema,
        streamName,
        specializedBlobClientBuilder,
        db,
        azureBlobConfig,
        nameTransformer,
        sqlOperations,
        new StagingFilenameGenerator(streamName, GlobalDataSizeConstants.DEFAULT_MAX_BATCH_SIZE_BYTES));
  }

}
