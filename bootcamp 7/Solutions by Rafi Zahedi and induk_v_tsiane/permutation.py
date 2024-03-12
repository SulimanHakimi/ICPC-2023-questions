# Solution by induk_v_tsiane

def solve():
    N = int(input())
    ans = 0
    pr = [-1] * (N * N)

    for i in range(1, N + 1):
        for j in range(1, N + 1):
            pr[i * j - 1] = 1

    for mx in range(N * N, 0, -1):
        if pr[mx - 1] == -1:
            continue
        a = [[] for _ in range(N)]
        curans = -mx
        br = False

        for j in range(N, 0, -1):
            num = min(mx // j, N)
            if num < 1:
                br = True
                break
            a[num - 1].append(j)

        if br:
            break

        s = []

        for i in range(N):
            s.append(i + 1)
            brk = False
            for x in a[i]:
                if not s:
                    brk = True
                    break
                curans += s.pop() * x
            if brk:
                break

        ans = max(ans, curans)

    print(ans)


for _ in range(int(input())):
    solve()
