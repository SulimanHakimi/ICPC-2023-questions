#include <utility>
#include <queue>
#include <cstring>
#include <cstdio>
#include <cstdlib>
using namespace std;

typedef pair<int,unsigned> piu;

struct road {
  int s;
  int d;
  int l;
};

road roads[202000];
int first[202000];
unsigned best[202000];

int comp(const void* vr1, const void* vr2) {
  const road* r1 = (const road*) vr1;
  const road* r2 = (const road*) vr2;
  int ret;
  ret = r1->s - r2->s;
  if(ret) return ret;
  ret = r1->d - r2->d;
  if(ret) return ret;
  ret = r1->l - r2->l;
  if(ret) return ret;
  return ret;
}

int dist(int s, int d) {
  memset(best,-1,sizeof(best));
  queue<piu> q;
  q.push(piu(s,0));
  while(q.size() > 0) {
    piu cl = q.front();
    q.pop();
    int c = cl.first;
    unsigned l = cl.second;
    if(l >= best[c]) continue;
    best[c] = l;
    if(l >= best[d]) continue;
    for(int i = first[c]; roads[i].s == c; i++) {
      q.push(piu(roads[i].d, l+roads[i].l));
    }
  }
  if(best[d] == -1) *((int*)5) = 0;
  return best[d];
}
int store[20];
int dists[20][20];
int perm[20];
int S;
unsigned bestest;
void print(const int *v, const int size)
{
  unsigned ret = 0;
  int i;
  if (v != 0) {
    for (i = 0; i < size; i++) {
      int j;
      if(i == 0) j = S; else j = v[i-1]-1;
      0&&printf("(%d,%d) ", j, v[i]-1);
      ret += dists[j][v[i]-1];
      0&&printf("d%d ", dists[j][v[i]-1]);
      0&&printf("%d ", v[i]);
    }
      0&&printf("(%d,%d) ", v[i-1]-1, S);
    ret += dists[v[i-1]-1][S];
      0&&printf("d%d ", dists[v[i-1]-1][S]);
    0&&printf("%d\n", ret);
    if(ret < bestest) bestest = ret;
  }
} // print


void visit(int *Value, int N, int k)
{
    int i;
  static int level = -1;
  level = level+1; Value[k] = level;

  if (level == N)
    print(Value, N);
  else
    for (i = 0; i < N; i++)
      if (Value[i] == 0)
        visit(Value, N, i);

  level = level-1; Value[k] = 0;
}

main() {
  int NN;
  scanf("%d", &NN);
  while(NN--) {
    int N, M;
    memset(roads, 0, sizeof(roads));
    memset(first, 0, sizeof(first));
    scanf("%d %d", &N, &M);
    for(int i = 0; i < M; i++) {
      scanf("%d %d %d", &roads[2*i].s, &roads[2*i].d, &roads[2*i].l);
      roads[2*i+1].s = roads[2*i].d;
      roads[2*i+1].d = roads[2*i].s;
      roads[2*i+1].l = roads[2*i].l;
    }
    qsort(roads, M*2, sizeof(road), comp);
    roads[M*2].s = -1;
    for(int i = 0; i < N; i++) {
      first[i] = M*2;
    }
    for(int i = 0; i < M*2; i++) {
      if(first[roads[i].s] == M*2) {
        first[roads[i].s] = i;
      }
    }
    scanf("%d", &S);
    for(int i = 0; i < S; i++) {
      scanf("%d", store+i);
    }
    store[S] = 0;
    for(int ii = 0; ii < S+1; ii++) {
      for(int jj = 0; jj < S+1; jj++) {
        int i = store[ii];
        int j = store[jj];
        dists[ii][jj] = dist(i,j);
      }
    }
    bestest = -1;
    for(int i = 0; i < S; i++) perm[i] = 0;
    visit(perm, S, 0);
    printf("%d\n", bestest);
  }
}
