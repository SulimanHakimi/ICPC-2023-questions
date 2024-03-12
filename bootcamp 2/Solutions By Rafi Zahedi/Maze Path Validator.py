# if you fight for it, you deserve it.
# if you deserve it, fight for it.

from collections import deque


def solve(m, n, start, destination, maze):
    visited = {tuple(start)}
    q = deque([tuple(start)])
    while len(q) > 0:
        x, y = q.popleft()
        if x == destination[0] and y == destination[1]:
            return True
        for dx, dy in [(-1, 0), (0, 1), (1, 0), (0, -1)]:
            nx, ny = x + dx, y + dy
            while 0 <= nx < m and 0 <= ny < n and maze[nx][ny] != 1:
                nx += dx
                ny += dy
            nx -= dx
            ny -= dy
            if (nx, ny) not in visited:
                q.append((nx, ny))
                visited.add((nx, ny))
    return False


row, col = map(int, input().split())
start = list(map(int, input().split()))
dst = list(map(int, input().split()))
mz = []
for _ in range(row):
    temp = list(map(int, input().split()))
    mz.append(temp)

if solve(row, col, start, dst, mz):
    print('true')
else:
    print('false')
