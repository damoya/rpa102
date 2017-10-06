package org.elyeva.rpa102.cltfx;

import es.indra.eplatform.EPException;
import es.indra.eplatform.data.DataUtils;
import es.indra.eplatform.data.datarow.IDataRow;
import es.indra.eplatform.data.query.IQuery;
import es.indra.eplatform.data.record.IRecordDataStore;
import es.indra.eplatform.services.ServiceEngineRunner;
import es.indra.eplatform.services.ServiceManagerException;
import es.indra.eplatformfx.data.ui.CltDataMgmtService;

public class Rpa102QueryTest {

	// @Before
	public void initialize() throws ServiceManagerException, EPException {
		ServiceEngineRunner runner = new ServiceEngineRunner();

		runner.startup(new String [] {"--config", "/config/rpa102-app-properties.xml"});
	}

	// @Test
	public void test1() throws EPException {
		CltDataMgmtService serv = CltDataMgmtService.getInstance();

		IRecordDataStore rds = serv.getRecordDataStore();

//		ITable tblClassRoomInc = rds.getMetadata().getTable(RpaDbConstants.TblClassroomIncidences.NAME);
//		ITable tblStudentsByYear = rds.getMetadata().getTable(RpaDbConstants.TblStudentsByAcademicYear.NAME);
//
//		QueryOptions.Builder builder = QueryOptions.builder();
//
//		builder.setId("query1");
//		builder.setRootTable("A", tblClassRoomInc);
//		builder.includeAllColumns("A");
//		builder.addRelatedTable("B", tblStudentsByYear);
//		builder.includeColumn("B", RpaDbConstants.TblStudentsByAcademicYear.COL_ACADEMIC_YEAR_ID);
//
//		builder.addJoin(
//				"A", RpaDbConstants.TblClassroomIncidences.COL_STUDENT_ID,
//				"B", RpaDbConstants.TblStudentsByAcademicYear.COL_STUDENT_ID);
//
//		Query query = new Query(builder.build());
//
//		IDataCollection<IDataRow> dc = rds.getDataCollection(query);
//
//		for (IDataRow dr : dc) {
//			DataUtils.print(dr);
//		}
//
//		System.out.println(dc);

		// --

		IQuery query2 = serv.getDataSourceContext().getMetadata().getQuery("queryStudentsWithDetail");

		for (IDataRow dr : rds.getDataCollection(query2)) {
			DataUtils.print(dr);
		}
	}
}
