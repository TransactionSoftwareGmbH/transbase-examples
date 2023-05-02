package de.transaction.demo.hibernate;

import static org.hibernate.type.SqlTypes.*;

import java.sql.Types;

import org.hibernate.MappingException;
import org.hibernate.dialect.DatabaseVersion;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.function.CommonFunctionFactory;
import org.hibernate.dialect.identity.IdentityColumnSupport;
import org.hibernate.dialect.identity.IdentityColumnSupportImpl;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolutionInfo;
import org.hibernate.query.spi.QueryEngine;

/**
 * An SQL dialect for Transbase.
 * This implementation requires Hibernate 6.x!
 */
public class TransbaseDialect extends Dialect
{
    public TransbaseDialect()
    {
        this(DatabaseVersion.make(8, 4, 1));
    }

    public TransbaseDialect(DatabaseVersion version)
    {
        super(version);
    }

    public TransbaseDialect(DialectResolutionInfo info)
    {
        super(info);
    }

    @Override
    public int getDefaultStatementBatchSize()
    {
        return 15;
    }

    @Override
    protected String columnType(int sqlTypeCode)
    {
        return switch (sqlTypeCode)
        {
            case BOOLEAN, BIT -> "bool";
            case BINARY, LONGVARBINARY, VARBINARY -> "binchar($1)";
            case TIMESTAMP -> "timestamp";
            case DOUBLE -> "double";
            case FLOAT, REAL -> "float";
            case DECIMAL -> "numeric($p,$s)";
            default -> super.columnType(sqlTypeCode);
        };
    }

    @Override
    public void initializeFunctionRegistry(QueryEngine queryEngine)
    {
        super.initializeFunctionRegistry(queryEngine);

        CommonFunctionFactory functionFactory = new CommonFunctionFactory(queryEngine);

        functionFactory.round_floor();
        functionFactory.trunc();
        //...
    }

    @Override
    public boolean doesRepeatableReadCauseReadersToBlockWriters()
    {
        return true;
    }

    @Override
    public boolean dropConstraints()
    {
        return false;
    }

    @Override
    public boolean qualifyIndexName()
    {
        return false;
    }

    @Override
    public IdentityColumnSupport getIdentityColumnSupport()
    {
        return new TransbaseIdentityColumnSupport();
    }

    @Override
    public boolean supportsIfExistsBeforeTableName()
    {
        return true;
    }

    @Override
    public boolean supportsOuterJoinForUpdate()
    {
        return false;
    }

    private static class TransbaseIdentityColumnSupport extends IdentityColumnSupportImpl
    {
        @Override
        public String getIdentityColumnString(int type)
        {
            final String auto = " auto_increment";
            return switch (type)
            {
                case Types.TINYINT -> "tinyint" + auto;
                case Types.SMALLINT -> "smallint" + auto;
                case Types.INTEGER -> "integer" + auto;
                case Types.BIGINT -> "bigint" + auto;
                default -> throw new MappingException("illegal identity column type");
            };
        }

        @Override
        public boolean hasDataTypeInIdentityColumn()
        {
            return false;
        }
    }
}
