#include<stdio.h>
#include<algorithm>
#include<vector>
#include<string.h>
#include<stdlib.h>
#include<deque>

#define ROOMCODE 100000000
#define STAIRCODE 200000000
#define FLOORCODE 100000
#define DOORCODE 300000000
#define DIVCODE 100000000

#define VERTICALMOVEMENT 3
#define HORIZONTALMOVEMENT 3

#define MAXFLOOR 5

using namespace std;

typedef struct triComponent {
   int num;
   int x, y;
} TRI;

typedef struct floorInfo {
   vector<vector<int> > cells;
   int width;
   int height;
   int floor;
   vector<TRI> doors;
   vector<TRI> stairs;
   vector<TRI> rooms;
   //int doorCnt;
   //int stairCnt;
   //int roomCnt;
} FLOOR;

FLOOR getRoomData(int floor);
void addRoomInfo(FLOOR *tar);
void getRoomToStair(FLOOR *tar, int floorCnt);
void getRoomToDoor(FLOOR *tar, int floorCnt);
void getStairToDoor(FLOOR *tar, int floorCnt);

int getDistanceTo(vector<vector<int> > tar, int sX, int sY, int tX, int tY);

int main() {
   //freopen("output.txt", "w", stdout);

   FLOOR floors[MAXFLOOR + 1];

   for (int i = 1; i <= MAXFLOOR; i++) {
      printf("Getting data - floor %d...\n", i);
      floors[i] = getRoomData(i);
      printf("Formatting data - floor %d...\n", i);
      addRoomInfo(floors + i);
      printf("Done - floor %d...\n", i);
   }

   printf("Calculating Room -> Stair at all floor\n");
   getRoomToStair(floors, MAXFLOOR);
   printf("Done calculating Room -> Stair at all floor\n");

   printf("Calculating Room -> Door at floor 1\n");
   getRoomToDoor(floors, MAXFLOOR);
   printf("Done calculating Room -> Door at floor 1\n");

   printf("Calculating Stair -> Door at floor 1\n");
   getStairToDoor(floors, MAXFLOOR);
   printf("Done calculating Stair -> Door at floor 1\n");


}

FLOOR getRoomData(int floor) {
   FLOOR ret;

   FILE *inputPtr;

   char fileName[50] = "floor";
   char floorNum[5]; _itoa(floor, floorNum, 10);
   strcat(fileName, floorNum);
   strcat(fileName, (char*)".txt");
   printf("%s ", fileName);
   inputPtr = fopen(fileName, "r");


   fscanf(inputPtr, "%d %d", &ret.width, &ret.height);
   printf("%d %d ", ret.width, ret.height);

   for (int i = 0; i < ret.height; i++) {
      vector<int> tmpInputVector;
      for (int j = 0; j < ret.width; j++) {
         int tmpInput;
         fscanf(inputPtr, "%d", &tmpInput);
         tmpInputVector.push_back(tmpInput);
      }
      ret.cells.push_back(tmpInputVector);

   }
   ret.floor = floor;

   printf("\n");

   return ret;
}

void addRoomInfo(FLOOR *tar) {
   for (int i = 0; i < tar->height; i++) {
      for (int j = 0; j < tar->width; j++) {
         TRI tmpTri;
         tmpTri.x = i;
         tmpTri.y = j;
         tmpTri.num = tar->cells[i][j];
         switch (tar->cells[i][j] / DIVCODE) {
         case 1: //room
            tar->rooms.push_back(tmpTri);
            break;
         case 2: //stair
            tar->stairs.push_back(tmpTri);
            break;
         case 3: //door
            tar->doors.push_back(tmpTri);
            break;
         default: //others
            break;
         }
      }
   }


}

void getRoomToStair(FLOOR *tar, int floorCnt) {
   // all the floor

   vector<vector<int> > ret;
   int stairCnt = (tar + 1)->stairs.size();

   for (int floorNum = 1; floorNum <= MAXFLOOR; floorNum++) {
      for (int tarRoom = 0; tarRoom < (tar + floorNum)->rooms.size(); tarRoom++){
         vector<int> tmp;
         tmp.push_back((tar+floorNum)->rooms[tarRoom].num);
         for (int tarStair = 0; tarStair < (tar + floorNum)->stairs.size(); tarStair++) {
            printf("target Door %d %d\n", (tar + floorNum)->rooms[tarRoom].x, (tar + floorNum)->rooms[tarRoom].y);
            int dist = getDistanceTo((tar + floorNum)->cells, (tar + floorNum)->rooms[tarRoom].x, (tar + floorNum)->rooms[tarRoom].y,
               (tar + floorNum)->stairs[tarStair].x, (tar + floorNum)->stairs[tarStair].y);
            tmp.push_back(dist);
         }
         ret.push_back(tmp);
         //printf("Done with Door %d\n", tmpVal);
      }
   }

   FILE *outPtr;
   outPtr = fopen("roomWithStair.txt", "w");

   fprintf(outPtr, "%d %d\n", ret.size(), stairCnt);
   for (int i = 0; i < ret.size(); i++) {
      fprintf(outPtr, "%d\n", ret[i][0]);
      for (int j = 1; j < ret[i].size(); j++) {
         fprintf(outPtr, "%d %d\n", (tar + 1)->stairs[j - 1].num, ret[i][j]);
      }
   }
}

void getRoomToDoor(FLOOR *tar, int floorCnt) {
   // only floor 1

   vector<vector<int> > ret;
   int doorCnt = (tar + 1)->doors.size();

   for (int floorNum = 1; floorNum <= 1; floorNum++) {
      for (int i = 0; i < (tar + floorNum)->height; i++) {
         for (int j = 0; j < (tar + floorNum)->width; j++) {
            int tmpVal = (tar + floorNum)->cells[i][j];
            if (tmpVal / DIVCODE == 1) {
               //printf("Found Room %d\n", tmpVal);
               vector<int> tmp;
               tmp.push_back(tmpVal);
               for (int tarDoor = 0; tarDoor < (tar + floorNum)->doors.size(); tarDoor++) {
                  //printf("target Door %d %d\n", (tar + floorNum)->doors[tarDoor].x, (tar + floorNum)->doors[tarDoor].y);
                  int dist = getDistanceTo((tar + floorNum)->cells, i, j,
                     (tar + floorNum)->doors[tarDoor].x, (tar + floorNum)->doors[tarDoor].y);
                  tmp.push_back(dist);
               }
               ret.push_back(tmp);
               //printf("Done with Door %d\n", tmpVal);
            }
         }
      }

   }

   FILE *outPtr;
   outPtr = fopen("roomAtFloor1.txt", "w");

   fprintf(outPtr, "%d %d\n", ret.size(), doorCnt);
   for (int i = 0; i < ret.size(); i++) {
      fprintf(outPtr, "%d\n", ret[i][0]);
      for (int j = 1; j < ret[i].size(); j++) {
         fprintf(outPtr, "%d %d\n", (tar + 1)->doors[j - 1].num, ret[i][j]);
      }
   }
}

void getStairToDoor(FLOOR *tar, int floorCnt) {
   // only floor 1

   vector<vector<int> > ret;
   int doorCnt = (tar + 1)->doors.size();

   for (int floorNum = 1; floorNum <= 1; floorNum++) {
      for (int i = 0; i < (tar + floorNum)->height; i++) {
         for (int j = 0; j < (tar + floorNum)->width; j++) {
            int tmpVal = (tar + floorNum)->cells[i][j];
            if (tmpVal / DIVCODE == 2) {
               vector<int> tmp;
               tmp.push_back(tmpVal);
               for (int tardoor = 0; tardoor < (tar + floorNum)->doors.size(); tardoor++) {
                  int dist = getDistanceTo((tar + floorNum)->cells, i, j,
                     (tar + floorNum)->doors[tardoor].x, (tar + floorNum)->doors[tardoor].y);
                  tmp.push_back(dist);
               }
               ret.push_back(tmp);
            }
         }
      }

   }

   FILE *outPtr;
   outPtr = fopen("stairWithDoor.txt", "w");

   fprintf(outPtr, "%d %d\n", ret.size(), doorCnt);
   for (int i = 0; i < ret.size(); i++) {
      fprintf(outPtr, "%d\n", ret[i][0]);
      for (int j = 1; j < ret[i].size(); j++) {
         fprintf(outPtr, "%d %d\n", (tar + 1)->doors[j - 1].num, ret[i][j]);
      }
   }
}

int getDistanceTo(vector<vector<int> > tar, int sX, int sY, int tX, int tY) {
   int height = tar.size();
   int width = tar[1].size();

   typedef struct actions {
      int dist;
      int x, y;
   } ACT;
   deque<ACT> acts;

   int dx[4] = { 0,0,1,-1 };
   int dy[4] = { 1,-1,0,0 };
   int db[4] = { VERTICALMOVEMENT,VERTICALMOVEMENT,
      HORIZONTALMOVEMENT,HORIZONTALMOVEMENT };

   ACT tmp;
   tmp.dist = 0;
   tmp.x = sX;
   tmp.y = sY;
   acts.push_back(tmp);

   while (!acts.empty()) {
      tmp = acts.front();
      acts.pop_front();

      //printf("!%d %d %d\n", tmp.x, tmp.y, tmp.dist);

      if (tmp.x == tX && tmp.y == tY) return tmp.dist;

      for (int d = 0; d < 4; d++) {
         int tarX = tmp.x + dx[d];
         int tarY = tmp.y + dy[d];

         //printf("%d %d:: %d %d -> %d %d\n", tarX, tarY,sX,sY,tX,tY);
         
         if (tar[tarX][tarY] != 1) {
            ACT putTmp;
            putTmp.x = tarX; putTmp.y = tarY;
            putTmp.dist = tmp.dist + db[d];
            if (tarX == tX && tarY == tY) return tmp.dist + db[d];
            tar[tarX][tarY] = 1;
            acts.push_back(putTmp);
         }
      }

   }

   printf("Can't find %d %d!!!\n", tX, tY);

   return -1;
}
