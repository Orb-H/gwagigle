# 과기글 repo

## 진행 정도
- [ ] floorN.txt
- [ ] roomWithStair.txt
- [ ] stairWithDoor.txt
- [ ] roomAtFloor1
- [ ] totalRoomShortCut
- [ ] totalRoomToLibWhenIdle
- [ ] totalRoomToLibWhenBusy

## 파일 형식
각 층마다 floor{N}.txt 형식으로 저장(확장자 불필요)
### ```floor{N}.txt```의 형식
```{width} {height}```

```num + " " + num + " " + ...``` 형식으로 지정
#### num의 종류
* 강의실 내부/벽 : ```1```
* 강의실 출입구 : ```100,000,000 + 강의실 번호```
* 계단 : ```200,000,000 + 층 * 100,000 + 계단 번호```
* 건물 출입구 : ```300,000,000 + 출입구 번호```

### roomWithStair.txt
#### 1차 계산 output / 2차 계산 input

```
{number of room} {number of stair}
{강의실 번호}
{계단 번호} {블럭 수}
{계단 번호} {블럭 수}
{계단 번호} {블럭 수}
{강의실 번호}
{계단 번호} {블럭 수}
{계단 번호} {블럭 수}
{계단 번호} {블럭 수}
...
```

강의실->계단까지의 블럭 수

```number of room * (number of stair + 1)``` 줄 필요

### stairWithDoor.txt
#### 1차 계산 output / 2차 계산 input
#### 5번 출구는 계단 1개만큼 추가

```
{number of stair at 1st floor} {number of door of building}
{계단 번호}
{건물 출입구 번호} {블럭 개수}
{건물 출입구 번호} {블럭 개수}
{계단 번호}
...
```

### roomAtFloor1
#### 1차 계산 output / 2차 계산 input

```
{number of room} {number of door of building}
{강의실 번호}
{출입구 번호} {블럭 개수}
{출입구 번호} {블럭 개수}
{강의실 번호}
...
```

### totalRoomShortcut
#### 2차 계산 output

```
{number of room} {number of door of building}
{강의실 번호}
{출입구 번호} {블럭 수}
{출입구 번호} {블럭 수}
{강의실 번호}
...
```

### totalRoomToLibWhenIdle
#### 3차 계산 output

```
{number of room}
{강의실 번호} {최단 시간 경로 출입구 번호} {최단 시간(초)}
...
```

### totalRoomToLibWhenBusy
#### 3차 계산 output

```
{number of room}
{강의실 번호} {최단 시간 경로 출입구 번호} {최단 시간(초)}
...
```
