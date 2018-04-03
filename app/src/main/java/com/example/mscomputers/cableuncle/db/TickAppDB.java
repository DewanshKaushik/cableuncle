package com.example.mscomputers.cableuncle.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by User on 7/25/2016.
 */
public class TickAppDB {

    private static TickAppDB _instance;
    private static final String NOM_BDD = "tick.db";

    private Context _ctx;
    private SQLiteDatabase sqLiteDatabase;
    private MaSqliteDatabase maBaseSqlite;

    public TickAppDB(Context context) {
        maBaseSqlite = new MaSqliteDatabase(context, NOM_BDD);
    }

    public static TickAppDB getInstance(Context ctx) {
        if (_instance == null)
            _instance = new TickAppDB(ctx);
        return _instance;
    }

    private void closeDB() {
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
            sqLiteDatabase.close();
    }

    public void open() {
        sqLiteDatabase = maBaseSqlite.getWritableDatabase();
    }

  /*  public void addTemplate(TemplateModel model) {
        try {
            if (isTempExist(model.name, model.id))
                deleteTemplateById(model.id);
            open();
            ContentValues cv = new ContentValues();
            cv.put(MaSqliteDatabase.KEY_TEMPLATE_NAME, model.name);
            cv.put(MaSqliteDatabase.KEY_TEMPLATE_ID, model.id);
            cv.put(MaSqliteDatabase.KEY_TEMPLATE_FILE, model.tempFile);
            sqLiteDatabase.insert(MaSqliteDatabase.TEMPLATE_TABLE_NAME, null, cv);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDB();
        }
    }

    public ArrayList<TemplateModel> getTemplatesList() {
        open();
        Cursor c = null;
        ArrayList<TemplateModel> templates = new ArrayList<>();
        try {
            c = sqLiteDatabase.query(MaSqliteDatabase.TEMPLATE_TABLE_NAME,
                    new String[]{MaSqliteDatabase.KEY_TEMPLATE_ID, MaSqliteDatabase.KEY_TEMPLATE_NAME, MaSqliteDatabase.KEY_TEMPLATE_FILE}, null,
                    null, null, null, null);
            if (c.getCount() == 0)
                return templates;

            while (c.moveToNext()) {
                String file = c.getString(2);
                TemplateModel template = Logger.parseTemplateData(file);
                template.tempFile = file;
                templates.add(template);
            }
            return templates;
        } finally {
            if (c != null)
                c.close();
            closeDB();
        }
    }

    public TemplateModel getTemplateData(int tempId) {
        open();
        Cursor c = null;
        TemplateModel template = null;
        try {
            c = sqLiteDatabase.query(MaSqliteDatabase.TEMPLATE_TABLE_NAME,
                    new String[]{MaSqliteDatabase.KEY_TEMPLATE_ID, MaSqliteDatabase.KEY_TEMPLATE_NAME, MaSqliteDatabase.KEY_TEMPLATE_FILE}, MaSqliteDatabase.KEY_TEMPLATE_ID + " = " + tempId,
                    null, null, null, null);
            if (c.getCount() == 0)
                return null;

            while (c.moveToNext()) {
                template = new TemplateModel();
                template.id = c.getString(0);
                template.name = c.getString(1);
                template.tempFile = c.getString(2);
            }
            return template;
        } finally {
            if (c != null)
                c.close();
            closeDB();
        }
    }

    public void deleteTemplateById(String tId) {
        open();
        sqLiteDatabase.delete(MaSqliteDatabase.TEMPLATE_TABLE_NAME,
                MaSqliteDatabase.KEY_TEMPLATE_ID + "= '" + tId + "'", null);
        closeDB();

    }

    public void deleteServiceReport(int sId) {
        // TODO Auto-generated method stub
        open();
        sqLiteDatabase.delete(MaSqliteDatabase.SERVICE_REPORT_TABLE_NAME,
                MaSqliteDatabase.KEY_SERVICE_REPORT_ID + "= " + sId + "", null);
        //  deleteAllServiceItem(sId);
        closeDB();

    }


    public void addServiceReportData(ServiceModel model) {
        try {
            deleteServiceReport(model.serId);
            open();
            ContentValues cv = new ContentValues();
            cv.put(MaSqliteDatabase.KEY_SERVICE_REPORT_FILE, model.serviceFile);
            sqLiteDatabase.insert(MaSqliteDatabase.SERVICE_REPORT_TABLE_NAME, null, cv);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDB();
        }
    }*//*

    public boolean isServiceReportExist(String registrationNo) {
        boolean isExist = false;
        open();
        Cursor cursor = null;
        //  String regNo = damageReportModel.regNo;
        String sql = "SELECT " + MaSqliteDatabase.KEY_SERVICE_REPORT_ID + " FROM " + MaSqliteDatabase.SERVICE_REPORT_TABLE_NAME + " WHERE " + MaSqliteDatabase.KEY_SERVICE_REPORT_REG_NO + " = '" + registrationNo + "'";
        cursor = sqLiteDatabase.rawQuery(sql, null);

        if (cursor.getCount() > 0) {
            isExist = true;
        } else {
            isExist = false;
        }
        closeDB();
        return isExist;
    }

    public boolean isServiceReportbyTemplateExist(String tempName) {
        boolean isExist = false;
        open();
        Cursor cursor = null;
        //  String regNo = damageReportModel.regNo;
        String sql = "SELECT " + MaSqliteDatabase.KEY_SERVICE_REPORT_ID + " FROM " + MaSqliteDatabase.SERVICE_REPORT_TABLE_NAME + " WHERE " + MaSqliteDatabase.KEY_SERVICE_REPORT_TEMPLATE + " = '" + tempName + "'";
        cursor = sqLiteDatabase.rawQuery(sql, null);

        if (cursor.getCount() > 0) {
            isExist = true;
        } else {
            isExist = false;
        }
        closeDB();
        return isExist;
    }
*//*

    public ArrayList<ServiceModel> getServiceReportData() {
        open();
        Cursor c = null;

        ArrayList<ServiceModel> serviceModelArrayList = new ArrayList<>();
        try {
            c = sqLiteDatabase.query(MaSqliteDatabase.SERVICE_REPORT_TABLE_NAME,
                    new String[]{MaSqliteDatabase.KEY_SERVICE_REPORT_ID,
                            MaSqliteDatabase.KEY_SERVICE_REPORT_FILE}, null,
                    null, null, null, null);
            if (c.getCount() == 0)
                return null;

            while (c.moveToNext()) {
                ServiceModel service = new ServiceModel();
                String file = c.getString(1);
                service = service.parseServiceData(file);
                service.serId = c.getInt(0);
                service.serviceFile = file;
                serviceModelArrayList.add(service);
            }
        } finally {
            if (c != null)
                c.close();
            closeDB();
            return serviceModelArrayList;

        }
    }


    public void deleteAllServiceReportbyTempId(String id) {
        open();
        Cursor c = null;

        try {
            c = sqLiteDatabase.query(MaSqliteDatabase.SERVICE_REPORT_TABLE_NAME,
                    new String[]{MaSqliteDatabase.KEY_SERVICE_REPORT_ID, null}, MaSqliteDatabase.KEY_TEMPLATE_ID + " = " + id,
                    null, null, null, null);
            if (c.getCount() == 0)
                return;

            while (c.moveToNext()) {
                ServiceModel section = new ServiceModel();
                section.serId = c.getInt(0);
                deleteServiceReport(section.serId);

            }
        } finally {
            if (c != null)
                c.close();
            //  closeDB();
        }
    }


    public ServiceModel getServiceReportData(int serviceId) {
        open();
        Cursor c = null;
        ServiceModel service = null;
        try {
            c = sqLiteDatabase.query(MaSqliteDatabase.SERVICE_REPORT_TABLE_NAME,
                    new String[]{MaSqliteDatabase.KEY_SERVICE_REPORT_ID,
                            MaSqliteDatabase.KEY_SERVICE_REPORT_FILE}, MaSqliteDatabase.KEY_SERVICE_REPORT_ID + " = " + serviceId + "",
                    null, null, null, null);
            if (c.getCount() == 0)
                return null;

            while (c.moveToNext()) {
                service = new ServiceModel();
                String file = c.getString(1);
                service = service.parseServiceData(file);
                service.serId = c.getInt(0);
                service.serviceFile = file;
            }
            return service;
        } finally {
            if (c != null)
                c.close();
            closeDB();
        }
    }

    public ArrayList<DamageReportModel> getAllDamageReportData(*//*int damageId*//*) {
        open();
        Cursor c = null;
        ArrayList<DamageReportModel> damageList = new ArrayList<DamageReportModel>();
        try {
            c = sqLiteDatabase.query(MaSqliteDatabase.DAMAGE_REPORT_TABLE_NAME,
                    new String[]{MaSqliteDatabase.KEY_DAMAGE_REPORT_ID, MaSqliteDatabase.KEY_DAMAGE_REPORT_REG_NO,
                            MaSqliteDatabase.KEY_DAMAGE_REPORT_MILEAGE, MaSqliteDatabase.KEY_DAMAGE_REPORT_PERSON_NAME,
                            MaSqliteDatabase.KEY_DAMAGE_REPORT_DATE_TIME, MaSqliteDatabase.KEY_DAMAGE_REPORT_VIDEO_LINK},*//* MaSqliteDatabase.KEY_DAMAGE_REPORT_ID + " = " + damageId*//*null,
                    null, null, null, null);
            if (c.getCount() == 0)
                return null;

            while (c.moveToNext()) {
                DamageReportModel damage = new DamageReportModel();
                damage.id = c.getInt(0);
                damage.regNo = c.getString(1);
                damage.mileage = c.getInt(2);
                damage.technician = c.getString(3);
                damage.dateAndTime = c.getString(4);
                damage.videoLink = c.getString(5);

                damageList.add(damage);
            }
        } finally {
            if (c != null)
                c.close();
            closeDB();
            return damageList;

        }
    }

    public void addDamageReportData(DamageReportModel model) {
        try {
            open();
            ContentValues cv = new ContentValues();
            cv.put(MaSqliteDatabase.KEY_DAMAGE_REPORT_REG_NO, model.regNo);
            cv.put(MaSqliteDatabase.KEY_DAMAGE_REPORT_MILEAGE, model.mileage);
            cv.put(MaSqliteDatabase.KEY_DAMAGE_REPORT_PERSON_NAME, model.technician);
            cv.put(MaSqliteDatabase.KEY_DAMAGE_REPORT_DATE_TIME, model.dateAndTime);
            cv.put(MaSqliteDatabase.VEHICLE_TYPE, model.vehicleType);
            cv.put(MaSqliteDatabase.KEY_DAMAGE_REPORT_VIDEO_LINK, model.videoLink);
            sqLiteDatabase.insert(MaSqliteDatabase.DAMAGE_REPORT_TABLE_NAME, null, cv);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDB();
        }
    }

    public void updateDamageReport(DamageReportModel model) {
        try {
            open();
            ContentValues cv = new ContentValues();
            cv.put(MaSqliteDatabase.KEY_DAMAGE_REPORT_REG_NO, model.regNo);
            cv.put(MaSqliteDatabase.KEY_DAMAGE_REPORT_MILEAGE, model.mileage);
            cv.put(MaSqliteDatabase.KEY_DAMAGE_REPORT_PERSON_NAME, model.technician);
            cv.put(MaSqliteDatabase.KEY_DAMAGE_REPORT_DATE_TIME, model.dateAndTime);
            cv.put(MaSqliteDatabase.VEHICLE_TYPE, model.vehicleType);
            cv.put(MaSqliteDatabase.KEY_DAMAGE_REPORT_VIDEO_LINK, model.videoLink);
            sqLiteDatabase.update(MaSqliteDatabase.DAMAGE_REPORT_TABLE_NAME, cv,
                    MaSqliteDatabase.KEY_DAMAGE_REPORT_ID + "=" + model.id, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDB();
        }
    }

    public void updateServiceReport(ServiceModel model) {
        open();
        try {
            ContentValues cv = new ContentValues();
            cv.put(MaSqliteDatabase.KEY_SERVICE_REPORT_FILE, model.serviceFile);
            sqLiteDatabase.update(MaSqliteDatabase.SERVICE_REPORT_TABLE_NAME, cv,
                    MaSqliteDatabase.KEY_SERVICE_REPORT_ID + "=" + model.serId, null);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        closeDB();
    }

    public boolean isDamageReportExist(String registrationNo) {
        boolean isExist = false;
        open();
        Cursor cursor = null;
        //  String regNo = damageReportModel.regNo;
        String sql = "SELECT " + MaSqliteDatabase.KEY_DAMAGE_REPORT_ID + " FROM " + MaSqliteDatabase.DAMAGE_REPORT_TABLE_NAME + " WHERE " + MaSqliteDatabase.KEY_DAMAGE_REPORT_REG_NO + " = '" + registrationNo + "'";
        cursor = sqLiteDatabase.rawQuery(sql, null);

        if (cursor.getCount() > 0) {
            isExist = true;
        } else {
            isExist = false;
        }
        closeDB();
        return isExist;
    }

    public boolean isTempExist(String tempName, String id) {
        boolean isExist = false;
        open();
        Cursor cursor = null;
        String sql;

        try {

            sql = "SELECT " + MaSqliteDatabase.KEY_TEMPLATE_ID + " FROM " + MaSqliteDatabase.TEMPLATE_TABLE_NAME + " WHERE "
                    + MaSqliteDatabase.KEY_TEMPLATE_NAME + " ='" + tempName + "'";
            cursor = sqLiteDatabase.rawQuery(sql, null);

            if (cursor.getCount() > 0) {
                isExist = true;
            } else {
                isExist = false;
            }
        } catch (Exception e) {
        }

        closeDB();
        return isExist;
    }

    public void addDamageReportCoOrdinateData(int id, ArrayList<DamageReportCoordinateModel> points) {

        deleteCoordinateByID(id);
        try {

            for (DamageReportCoordinateModel model : points) {
                if (model != null) {
                    open();
                    ContentValues cv = new ContentValues();
                    cv.put(MaSqliteDatabase.KEY_DAMAGE_REPORT_ID, model.id);
                    cv.put(MaSqliteDatabase.KEY_DAMAGE_REPORT_COORDINATE_DAMAGE_TYPE, model.damageType);
                    cv.put(MaSqliteDatabase.KEY_DAMAGE_REPORT_COORDINATE_X_AXIS, model.getXAxis());
                    cv.put(MaSqliteDatabase.KEY_DAMAGE_REPORT_COORDINATE_Y_AXIS, model.getYAxis());
                    sqLiteDatabase.insert(MaSqliteDatabase.DAMAGE_REPORT_COORDINATE_TABLE_NAME, null, cv);

                    closeDB();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDB();
        }

    }

    public ArrayList<DamageReportCoordinateModel> getDamageReportCoordinateData(int damageId) {
        open();
        Cursor c = null;
        ArrayList<DamageReportCoordinateModel> damageCoordinates = new ArrayList<DamageReportCoordinateModel>();
        try {
            c = sqLiteDatabase.query(MaSqliteDatabase.DAMAGE_REPORT_COORDINATE_TABLE_NAME,
                    new String[]{MaSqliteDatabase.KEY_DAMAGE_REPORT_ID, MaSqliteDatabase.KEY_DAMAGE_REPORT_COORDINATE_DAMAGE_TYPE,
                            MaSqliteDatabase.KEY_DAMAGE_REPORT_COORDINATE_X_AXIS, MaSqliteDatabase.KEY_DAMAGE_REPORT_COORDINATE_Y_AXIS},
                    MaSqliteDatabase.KEY_DAMAGE_REPORT_ID + " = " + damageId,
                    null, null, null, null);
            if (c.getCount() == 0)
                return null;

            while (c.moveToNext()) {
                int xAxis = c.getInt(2);
                int yAxis = c.getInt(3);
                DamageReportCoordinateModel damage = new DamageReportCoordinateModel(xAxis, yAxis);
                damage.id = c.getInt(0);
                damage.damageType = c.getInt(1);
                damageCoordinates.add(damage);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null)
                c.close();
            closeDB();

            return damageCoordinates;
        }
    }


    public int getLastDamageReportId() {
        open();
        Cursor c = null;
        int number = 0;
        try {
            c = sqLiteDatabase.query(MaSqliteDatabase.DAMAGE_REPORT_TABLE_NAME,
                    new String[]{MaSqliteDatabase.KEY_DAMAGE_REPORT_ID},
                    null, null, null,
                    null, null);

            if (c.getCount() == 0)
                return number;
            while (c.moveToNext()) {
                number = c.getInt(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null)
                c.close();
            closeDB();
            return number;
        }
    }

    public int getLastDamageReportCorId(int damageId) {
        open();
        Cursor c = null;
        int number = 0;
        try {
            c = sqLiteDatabase.query(MaSqliteDatabase.DAMAGE_REPORT_COORDINATE_TABLE_NAME,
                    new String[]{MaSqliteDatabase.KEY_DAMAGE_REPORT_ID},
                    MaSqliteDatabase.KEY_DAMAGE_REPORT_ID + " = " + damageId, null, null,
                    null, null);


            number = c.getCount();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null)
                c.close();
            closeDB();
            return number;
        }
    }

    public void deleteCoordinateByID(int Id) {
        open();
        sqLiteDatabase.delete(MaSqliteDatabase.DAMAGE_REPORT_COORDINATE_TABLE_NAME,
                MaSqliteDatabase.KEY_DAMAGE_REPORT_ID + "= " + Id, null);
        closeDB();
    }

    public void deleteDamageReport(int damageReportId) {
        deleteCoordinateByID(damageReportId);
        open();
        sqLiteDatabase.delete(MaSqliteDatabase.DAMAGE_REPORT_TABLE_NAME,
                MaSqliteDatabase.KEY_DAMAGE_REPORT_ID + "= " + damageReportId, null);
        closeDB();
    }

    public DamageReportModel getDamageReportData(int damageId) {
        open();
        Cursor c = null;
        DamageReportModel damage = null;
        try {
            c = sqLiteDatabase.query(MaSqliteDatabase.DAMAGE_REPORT_TABLE_NAME,
                    new String[]{MaSqliteDatabase.KEY_DAMAGE_REPORT_ID, MaSqliteDatabase.KEY_DAMAGE_REPORT_REG_NO,
                            MaSqliteDatabase.KEY_DAMAGE_REPORT_MILEAGE, MaSqliteDatabase.KEY_DAMAGE_REPORT_PERSON_NAME,
                            MaSqliteDatabase.KEY_DAMAGE_REPORT_DATE_TIME, MaSqliteDatabase.VEHICLE_TYPE, MaSqliteDatabase.KEY_DAMAGE_REPORT_VIDEO_LINK}, MaSqliteDatabase.KEY_DAMAGE_REPORT_ID + " = " + damageId,
                    null, null, null, null);
            if (c.getCount() == 0)
                return null;

            while (c.moveToNext()) {
                damage = new DamageReportModel();
                damage.id = c.getInt(0);
                damage.regNo = c.getString(1);
                damage.mileage = c.getInt(2);
                damage.technician = c.getString(3);
                damage.dateAndTime = c.getString(4);
                damage.vehicleType = c.getString(5);
                damage.videoLink = c.getString(6);
            }
            return damage;
        } finally {
            if (c != null)
                c.close();
            closeDB();
        }
    }
*//*
    public void addServiceItem(int sid, ArrayList<SectionModel> sections) {
        try {
            open();
            deleteAllServiceItem(sid);
            for (SectionModel sedMod : sections) {
                for (ItemModel model : sedMod.items) {
                    ContentValues cv = new ContentValues();
                    cv.put(MaSqliteDatabase.KEY_ITEM_ID, model.id);
                    cv.put(MaSqliteDatabase.KEY_SERVICE_REPORT_ID, model.serviceId);
                    cv.put(MaSqliteDatabase.KEY_ITEM_CONDITION, model.itemCondition);
                    cv.put(MaSqliteDatabase.KEY_ITEM_DESC, model.itemDesc);
                    cv.put(MaSqliteDatabase.KEY_ITEM_IMAGE, model.itemImage);
                    cv.put(MaSqliteDatabase.KEY_ITEM_VIDEO, model.itemLocalVideo);
                    sqLiteDatabase.insert(MaSqliteDatabase.SERVICE_ITEM_TABLE_NAME, null, cv);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDB();
        }
    }

    public void deleteAllServiceItem(int sid) {
        try {
            sqLiteDatabase.delete(MaSqliteDatabase.SERVICE_ITEM_TABLE_NAME, MaSqliteDatabase.KEY_SERVICE_REPORT_ID + "= " + sid, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    public ArrayList<ItemModel> getServiceItems(int serId, String itemIds) {
        open();
        Cursor c = null;
        ArrayList<ItemModel> items = new ArrayList<>();
        try {

            String query = "SELECT * FROM " + MaSqliteDatabase.SERVICE_ITEM_TABLE_NAME +
                    " WHERE " + MaSqliteDatabase.KEY_ITEM_ID + " IN (" + itemIds + ") AND " + MaSqliteDatabase.KEY_SERVICE_REPORT_ID + " = " + serId;
            *//**//*c = sqLiteDatabase.query(MaSqliteDatabase.SERVICE_ITEM_TABLE_NAME,
                    new String[]{MaSqliteDatabase.KEY_ITEM_ID, MaSqliteDatabase.KEY_SERVICE_REPORT_ID, MaSqliteDatabase.KEY_ITEM_VIDEO, MaSqliteDatabase.KEY_ITEM_IMAGE, MaSqliteDatabase.KEY_ITEM_DESC, MaSqliteDatabase.KEY_ITEM_CONDITION}, MaSqliteDatabase.KEY_SERVICE_REPORT_ID + " = " + id,
                    null, null, null, null);*//**//*
            c = sqLiteDatabase.rawQuery(query, null);
            if (c.getCount() == 0)
                return items;

            while (c.moveToNext()) {
                ItemModel item = new ItemModel();
                item.id = c.getInt(0);
                item.serviceId = c.getInt(1);
                item.itemCondition = c.getString(2);
                item.itemDesc = c.getString(3);
                item.itemImage = c.getString(4);
                item.itemLocalVideo = c.getString(5);
                items.add(item);
            }
            return items;
        } finally {
            if (c != null)
                c.close();
            closeDB();
        }
    }*//*

    public int getLastServiceReportId() {
        open();
        Cursor c = null;
        int number = 0;
        try {
            c = sqLiteDatabase.query(MaSqliteDatabase.SERVICE_REPORT_TABLE_NAME,
                    new String[]{MaSqliteDatabase.KEY_SERVICE_REPORT_ID},
                    null, null, null,
                    null, null);

            if (c.getCount() == 0)
                return number;
            while (c.moveToNext()) {
                number = c.getInt(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null)
                c.close();
            closeDB();
            return number;
        }
    }

*//*

    public ItemModel getServiceItemData(int itemId) {
        open();
        Cursor c = null;
        ItemModel item = null;
        try {
            c = sqLiteDatabase.query(MaSqliteDatabase.SERVICE_ITEM_TABLE_NAME,
                    new String[]{MaSqliteDatabase.KEY_ITEM_ID, MaSqliteDatabase.KEY_SERVICE_REPORT_ID, MaSqliteDatabase.KEY_ITEM_VIDEO, MaSqliteDatabase.KEY_ITEM_IMAGE, MaSqliteDatabase.KEY_ITEM_DESC, MaSqliteDatabase.KEY_ITEM_CONDITION}, MaSqliteDatabase.KEY_ITEM_ID + " = " + itemId,
                    null, null, null, null);
            if (c.getCount() == 0)
                return item;

            while (c.moveToNext()) {
                item = new ItemModel();
                item.id = c.getInt(0);
                item.serviceId = c.getInt(1);
                item.itemLocalVideo = c.getString(2);
                item.itemImage = c.getString(3);
                item.itemDesc = c.getString(4);
                item.itemCondition = c.getString(5);
            }
            return item;
        } finally {
            if (c != null)
                c.close();
            closeDB();
        }
    }
*//*
*/

}
