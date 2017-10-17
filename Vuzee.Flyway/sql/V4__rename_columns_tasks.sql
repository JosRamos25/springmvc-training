ALTER TABLE "public"."tasks" ADD COLUMN "has_rating" bool NOT NULL;
ALTER TABLE "public"."tasks" DROP COLUMN "hasrating";
ALTER TABLE "public"."tasks" ADD COLUMN "task_state" int4 NOT NULL;
ALTER TABLE "public"."tasks" DROP COLUMN "task_type";